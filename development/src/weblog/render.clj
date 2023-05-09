(ns weblog.render
  (:require
   [babashka.fs :as fs]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.data.xml :as xml]
   [clojure.set :as cset]
   [clojure.string :as cstr]
   [markdown.core :as md]
   [selmer.parser :as selmer]))

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  false)

(def create-assets
  "Return the assets directory that has been requested. Create it if it
  does not exist."
  (memoize (fn [parent-dir assets-dir-name]
             (fs/create-dirs (fs/file parent-dir assets-dir-name)))))

(defn debug-msg
  "Print `msg`, if `*print-debug* is true"
  [msg]
  (when *print-debug* (println msg)))

(defn copy-assets
  "Copy assets into the public dir. Used to sync images and CSS."
  [opts]
  (let [public (create-assets (:public-dir opts) (:public-assets-dir-name opts))
        local (create-assets (:content-dir opts) (:local-assets-dir-name opts))]
    (println (str "Copying assets from: " local " to: " public))
    (fs/copy-tree local public {:replace-existing true})))

(def read-template
  "Given the `templates-dir` and the `template`, return the contents of
  the template file at the root of the dir."
  (memoize (fn [templates-dir template]
             (slurp (fs/file templates-dir template)))))

(defn pre-process-markdown
  "Process the markdown before converting it to HTML. This is a
workaround for https://github.com/yogthos/markdown-clj/issues/146"
  [markdown]
  (-> markdown
      ;; Replace -- with a temp value to ensure `md-to-html-string`
      ;; does not mess with it.
      (cstr/replace #" -- " (fn [_] "$$NDASH$$"))
      ;; allow links with markup over multiple lines
      (cstr/replace #"\[[^\]]+\n"
                    (fn [match] (cstr/replace match "\n" "$$RET$$")))))

(defn md-to-html
  "Take a string of markdown, slurped from a post file, and convert it to
  HTML. Handle the case of the actual file-contents being empty, and
  only a stub-entry existing.

  Return a data-map of the post."
  [markdown post-file]
  (try (md/md-to-html-string-with-meta
        markdown
        :code-style (fn [lang] (format "class=\"lang-%s\"" lang))
        :footnotes? true
        :heading-anchors true)
       (catch java.lang.NullPointerException _
         (debug-msg (str "-- Looks like " post-file " is empty!"))
         (if-let [m (md/md-to-meta markdown)]
           {:metadata m :html "" :stub? true}
           ;; Looks like the whole file is empty.
           (throw (ex-info "Encountered an empty file."
                           {:md-filename post-file}))))))

(defn post-process-html
  "Run the generated HTML through some post-processing. This is a
  workaround for https://github.com/yogthos/markdown-clj/issues/146"
  [html-map]
  (-> html-map
      ;; Get back our -- and newlines, undoing `pre-process-markdown`
      (update :html cstr/replace "$$NDASH$$" " -- ")
      (update :html cstr/replace "$$RET$$" "\n")))

(defn gen-id
  "Store the unique ID that identifies this post into the metadata.

  This is simply the name of the file, without any extension. This
  name is guaranteed to be unique by ox-neuron."
  [metadata]
  (let [id (-> metadata
               :md-filename
               fs/components
               last
               (cstr/replace ".md" ""))]
    (assoc metadata :id id)))

(defn gen-slug
  "Given the metadata of a post, generate a slug for the post if it does
not already exist."
  [metadata]
  {:pre [(seq (:id metadata))]}
  (if (nil? (:slug metadata))
    (do
      (debug-msg "No slug found! Using id as slug")
      (debug-msg (str "Title: " (:title metadata)
                      ", Slug: " (:id metadata)))
      (assoc metadata :slug (:id metadata)))
    ;; No change
    metadata))

(defn gen-html-filename
  "Given the metadata of a post, generate the html-filename for the post
from it's slug."
  [metadata]
  {:pre [(seq (:slug metadata))]}
  (if (nil? (:html-filename metadata))
    (let [html-filename (str (:slug metadata) ".html")]
      (debug-msg "No html-filename found! Generating one from the slug")
      (debug-msg (str "Title: " (:title metadata)
                    ", HTML file-name: " html-filename))
      (assoc metadata :html-filename html-filename))
    ;; No change
    metadata))

(defn merge-defaults
  "Given a `parsed-post`, merge default values of metadata fields into
  the post."
  [parsed-post post-file]
  (-> parsed-post
      (update-in [:metadata :date]
                 (fn [d]
                   (if (instance? java.util.Date d)
                     (.toInstant d)
                     (cjtz/to-instant (cjtz/now)))))
      (update :metadata assoc :md-filename post-file)
      (update-in [:metadata :tags] set)
      ;; *NOTE*: Categories are vectors. This means that inheritance
      ;; always appends to the end. So the first of the `categories`
      ;; vector will always return the category closest to the child.
      (update-in [:metadata :categories] vec)
      (update-in [:metadata :parents] set)
      (update-in [:metadata :children] set)

      (update :metadata gen-id)
      ;; Note that ID generation should happen before Slug, since slug
      ;; generation depends on the ID.
      (update :metadata gen-slug)
      ;; Note that HTML filename can only be generated after the slug,
      ;; since it depends on the slug.
      (update :metadata gen-html-filename)))

(defn post->data
  "Convert `post-file` from md format to HTML format"
  [post-file opts]
  (debug-msg (str "Converting from Markdown to HTML: " post-file))
  (let [file-contents (pre-process-markdown (slurp post-file))
        ;; Convert markdown to HTML. This breaks if the actual content
        ;; is empty, which seems like a bug no one has encountered
        ;; because no-one uses markdown-clj for brain forests (which
        ;; tend to have lots of placeholder files).
        initial-html-map (md-to-html file-contents post-file)
        html-map (post-process-html initial-html-map)]
    (merge-defaults html-map post-file)))

(def redirect-template
  "<html><head>
<meta http-equiv=\"refresh\" content=\"0; URL=/{{new_url}}\" />
</head></html>")

(defn render-redirect-file
  "Given the `old-url` and the `redirect-html`, write out the HTML."
  [old-url metadata opts]
  ;; Assumptions:
  ;; 1. All old URLS / slugs end with a / (assuming an implicit index.html)
  ;; 2. Data written to old URL / slugs don't overwrite actual posts
  (let [redirect-html (selmer/render redirect-template
                                     {:new_url (:html-filename metadata)})
        l (if (cstr/starts-with? old-url "/")
            (cstr/replace-first old-url "/" "")
            old-url)]
    (debug-msg (str "Writing redirects for old urls: " l "index.html"))
    (fs/create-dirs (fs/file (:public-dir opts) l))
    (spit (fs/file (:public-dir opts) l "index.html") redirect-html)))

(defn- render-post*
  [{:keys [metadata html]} opts]
  (let [base-html (read-template (:templates-dir opts) "base.html")
        post-html (read-template (:templates-dir opts) "post.html")
        post-body (selmer/render post-html
                                 (assoc metadata :body html))
        page-html (selmer/render base-html
                                 (merge opts
                                        metadata
                                        {:body post-body
                                         :discuss-link (str (:discuss-link opts)
                                                            (:md-filename metadata))}))
        html-file (fs/file (:public-dir opts)
                           (:html-filename metadata))]

    (debug-msg (str "Writing HTML to public-dir: " html-file))
    (fs/create-dirs (fs/parent html-file))
    (spit html-file page-html)

    (doseq [old-url (:aliases metadata)]
      (render-redirect-file old-url metadata opts))

    ;; On success, return ID of the post. Else something above will
    ;; throw an error.
    (:id metadata)))

(defn render-post
  "Given a `post` data-structure, write out the HTML for the post to the
  public folder."
  [opts post]
  (if (get-in post [:metadata :draft])
    (when (:publish-drafts? opts)
      (render-post* post opts))
    (render-post* post opts)))

(def local-parent-child-pairs-xform
  "Content files are nested according to relationships ->

     content/a/b.md means that b is a child of a
     content/a/b/c.md means that c is a child of b, b is a child of a.

  For the given filename, return all such parent-child tuples"
  (comp
   ;; Break filename at path and discard "contents/" This
   ;; is the list of all the files to act on.
   (map (comp rest fs/components fs/file :md-filename :metadata))
   ;; We only need to act on child entries to build the
   ;; necessary relationships. Root entries can be
   ;; skipped.
   (filter second)
   ;; Each component represents an entry. We pass it through gen-id as
   ;; a way to strip the .md prefix.
   ;; @TODO: Clean this up, it's unnecessarily complicated
   (map (fn [components] (map (comp :id gen-id #(hash-map :md-filename %))
                              components)))
   ;; Return parent/child tuples that can be acted on
   (mapcat (fn [fc] (->> fc rest (interleave fc) (partition 2))))))

(defn- update-parent-child-relationships
  "Helper function to update the metadata of entries with information
  about parenthood and childhood."
  [post-id->data [parent-id child-id]]
  (-> post-id->data
      (update-in [parent-id :metadata :children] conj child-id)
      (update-in [child-id :metadata :parents] conj parent-id)
      (update-in [child-id :metadata :tags]
                 cset/union
                 (get-in post-id->data [parent-id :metadata :tags]))
      (update-in [child-id :metadata :categories]
                 into
                 (get-in post-id->data [parent-id :metadata :categories]))))

(defn build-site
  "This is the main function.

  It is called from the bb task `render` to build all the HTML."
  [opts]
  (when (:print-debug opts)
    (alter-var-root #'*print-debug* (constantly true)))
  (copy-assets opts)
  (let [posts (fs/glob (:content-dir opts) "**.md")
        post-id->data-1 (reduce (fn [pid->p post-file]
                                (let [p (post->data (str post-file) opts)]
                                  (assoc pid->p (get-in p [:metadata :id]) p)))
                              {}
                              posts)
        local-parent-child-pairs (transduce local-parent-child-pairs-xform
                                            conj
                                            (vals post-id->data-1))
        post-id->data-2 (reduce update-parent-child-relationships
                                post-id->data-1
                                local-parent-child-pairs)]
    (doall
     (keep (comp (partial render-post opts) second)
           post-id->data-2))))
