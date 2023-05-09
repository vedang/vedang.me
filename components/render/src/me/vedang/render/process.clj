(ns me.vedang.render.process
  "Given an `html-map`, process the data and plug defaults into it."
  (:require
   [babashka.fs :as fs]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.string :as cstr]
   [me.vedang.logger.interface :as logger]))

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
      (logger/log "No slug found! Using id as slug")
      (logger/log (str "Title: " (:title metadata) ", Slug: " (:id metadata)))
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
  [html-map post-file]
  (-> html-map
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
