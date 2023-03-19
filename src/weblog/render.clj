(ns weblog.render
  (:require
   [babashka.fs :as fs]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.data.xml :as xml]
   [clojure.string :as cstr]
   [markdown.core :as md]))

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  false)

(def base-html
  "Return the base-html template from the templates-dir.

  @TODO: The name of the template is hard-coded at the moment, fix this."
  (memoize (fn [templates-dir]
             (slurp (fs/file templates-dir "base.html")))))

(def create-assets
  "Return the assets directory that has been requested. Create it if it
  does not exist."
  (memoize (fn [parent-dir assets-dir-name]
             (fs/create-dirs (fs/file parent-dir assets-dir-name)))))

(defn debug-msg
  "Print `msg`, if `*print-debug* is true"
  [msg]
  (when *print-debug* (println msg)))

;;;; Sync images and CSS
(defn copy-assets
  "Copy assets into the public dir"
  [opts]
  (let [public (create-assets (:public-dir opts) (:public-assets-dir-name opts))
        local (create-assets (:content-dir opts) (:local-assets-dir-name opts))]
    (println (str "Copying assets from: " local " to: " public))
    (fs/copy-tree local public {:replace-existing true})))

(def read-base-html
  "Given the `templates-dir`, return the contents of the base.html file at
  the root of the dir."
  (memoize (fn [templates-dir]
             (slurp (fs/file templates-dir "base.html")))))

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
      ;; Get back our -- and newlines.
      (update :html cstr/replace "$$NDASH$$" " -- ")
      (update :html cstr/replace "$$RET$$" "\n")))

(defn gen-id
  "Store the unique ID that identifies this post into the metadata.

  At the moment, this is the name of the exported file, without the
  .md extension.

  @TODO: In the future, I will export the ID property of the note and
  use this instead."
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
  (let [base-html (read-base-html (:templates-dir opts))
        file-contents (pre-process-markdown (slurp post-file))
        ;; Convert markdown to HTML. This breaks if the actual content
        ;; is empty, which seems like a bug no one has encountered
        ;; because no-one uses markdown-clj for brain forests (which
        ;; tend to have lots of placeholder files).
        initial-html-map (md-to-html file-contents post-file)
        html-map (post-process-html initial-html-map)]
    (merge-defaults html-map post-file)))

(defn build-site
  "This is the main function.

  It is called from the bb task `render` to build all the HTML."
  [opts]
  (copy-assets opts)
  (let [posts (fs/glob (:content-dir opts) "**.md")]
    (reduce (fn [pid->p post-file]
              (let [p (post->data (str post-file) opts)]
                (assoc pid->p (get-in p [:metadata :id]) p)))
            {}
            posts)))
