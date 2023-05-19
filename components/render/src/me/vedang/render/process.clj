(ns me.vedang.render.process
  "Process `html-map` data objects for fun and profit"
  (:require
   [babashka.fs :as fs]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.set :as cset]
   [clojure.string :as cstr]
   [me.vedang.logger.interface :as logger]))

(defn make-id
  [filename]
  (-> filename
      fs/components
      last
      (cstr/replace ".md" "")))

(defn gen-id
  "Store the unique ID that identifies this post into the metadata.

  This is simply the name of the file, without any extension. This
  name is guaranteed to be unique by ox-neuron."
  [metadata]
  (->> metadata :md-filename make-id (assoc metadata :id)))

(defn gen-slug
  "Given the metadata of a post, generate a slug for the post if it does
not already exist."
  [metadata]
  {:pre [(seq (:id metadata))]}
  (if (nil? (:slug metadata))
    (do
      (logger/log "No slug found! Using id as slug")
      (logger/log :info (str "Title: " (:title metadata) ", Slug: " (:id metadata)))
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
      (logger/log "No html-filename found! Generating one from the slug")
      (logger/log (str "Title: " (:title metadata) ", HTML file-name: " html-filename))
      (assoc metadata :html-filename html-filename))
    ;; No change
    metadata))

(defn merge-defaults
  "Given an `html-map`, merge default values of metadata fields into
  the post."
  [html-map]
  (-> html-map
      (update-in [:metadata :date]
                 (fn [d]
                   (if (instance? java.util.Date d)
                     (.toInstant d)
                     (cjtz/to-instant (cjtz/now)))))
      (update-in [:metadata :tags] set)
      ;; *NOTE*: Categories are vectors. This means that inheritance
      ;; always appends to the end. So the first of the `categories`
      ;; vector will always return the category closest to the child.
      (update-in [:metadata :categories] vec)
      (update-in [:metadata :parents] set)
      (update-in [:metadata :children] set)
      (update-in [:metadata :backlinks] set)

      (update :metadata gen-id)
      ;; Note that ID generation should happen before Slug, since slug
      ;; generation depends on the ID.
      (update :metadata gen-slug)
      ;; Note that HTML filename can only be generated after the slug,
      ;; since it depends on the slug.
      (update :metadata gen-html-filename)))

(defn get-content-path
  "Given an `html-map` object, return the relative path of the content."
  [html-map]
  (cstr/replace-first (:md-filename (:metadata html-map))
                      (:root-dir (:metadata html-map))
                      ""))

(def local-parent-child-pairs-xform
  "Content files are nested according to relationships ->

     content/a/b.md means that b is a child of a
     content/a/b/c.md means that c is a child of b, b is a child of a.

  For the given filename, return all such parent-child tuples"
  (comp
   ;; Break filename at path and discard the `content-dir` part. This
   ;; is the list of all the files to act on.
   (map (comp fs/components get-content-path))
   ;; We only need to act on child entries to build the necessary
   ;; relationships. Root entries can be skipped.
   (filter second)
   ;; Each component represents an entry. We pass it through gen-id as
   ;; a way to strip the .md prefix.
   (map (partial map make-id))
   ;; Return parent/child tuples that can be acted on
   (mapcat (fn [fc] (->> fc rest (interleave fc) (partition 2))))))

(defn- update-parent-child-relationships
  "Helper function to update the metadata of entries with information
  about parenthood and childhood."
  [id->hmap [parent-id child-id]]
  (-> id->hmap
      (update-in [parent-id :metadata :children] conj child-id)
      (update-in [child-id :metadata :parents] conj parent-id)
      (update-in [child-id :metadata :tags]
                 cset/union
                 (get-in id->hmap [parent-id :metadata :tags]))
      (update-in [child-id :metadata :categories]
                 into
                 (get-in id->hmap [parent-id :metadata :categories]))))

(defn id->html-map
  "Build an in-mem data-structure of all the post content for later processing"
  [html-maps]
  (let [html-maps (map merge-defaults html-maps)
        local-parent-child-pairs (transduce local-parent-child-pairs-xform
                                            conj
                                            html-maps)
        id->hmap (reduce (fn [id->h html-map]
                           (assoc id->h
                                  (get-in html-map [:metadata :id])
                                  html-map))
                         {}
                         html-maps)]
    (reduce update-parent-child-relationships
            id->hmap
            local-parent-child-pairs)))

(defn sort-and-filter
  "Sort `html-maps` by date of publication. Filter the ones which are empty."
  [html-maps]
  (let [filter-xform (comp
                      ;; Keep only those posts which have actual HTML content
                      (filter (comp seq :html))
                      ;; Remove special pages like index.html, which
                      ;; should not show up in the archive. @TODO: We
                      ;; might need a better way to do this once we
                      ;; have Clerk notebooks in place.
                      (filter (comp not :skip-archive :metadata))
                      ;; Remove posts which are in draft stage @TODO:
                      ;; How do we handle `:publish-drafts` option
                      ;; here?
                      (filter (comp not :draft :metadata)))]
    (sort-by (comp :date :metadata)
             (comp - compare)
             (into [] filter-xform html-maps))))

(defn tag->html-map
  "Build an in-mem data-structure of all the post content for later
  processing, indexed by tag."
  [html-maps]
  (reduce (fn [tag->h {:keys [metadata] :as html-map}]
            (reduce (fn [t->h t] (update t->h t conj html-map))
                    tag->h
                    (:tags metadata)))
          {}
          html-maps))
