(ns me.vedang.render.core
  (:require
   [babashka.fs :as fs]
   [clojure.data.xml :as xml]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.index :as index]
   [me.vedang.render.markdown :as markdown]
   [me.vedang.render.page :as page]
   [me.vedang.render.post :as post]
   [me.vedang.render.process :as process]
   [me.vedang.render.redirect :as redirect]))

(defn post->html-map
  "Convert `post-file` from md format to HTML format"
  [content-dir post-file]
  (logger/log (str "Converting from Markdown to HTML: " post-file))
  (-> post-file
      slurp
      (markdown/md-to-html post-file)
      (update :metadata assoc :md-filename post-file)
      (update :metadata assoc :content-dir content-dir)))

(defn render-post
  "Given an `html-map`, write out the HTML for the post to the public folder."
  [{:keys [metadata] :as html-map} opts]
  (when (or (not (:draft metadata)) (:publish-drafts opts))
    (-> html-map
        (post/add-html-body opts)
        (page/render-file opts))
    (doseq [old-url (:aliases metadata)]
      (redirect/render-file old-url (:html-filename metadata) opts))
    html-map))

(defn build-posts
  [opts]
  (let [html-maps (mapv (comp (partial post->html-map (:content-dir opts)) str)
                        (fs/glob (:content-dir opts) "**.md"))
        id->html-map (process/id->html-map html-maps)]
    (when-not (:no-output opts)
      (doseq [[_id html-map] id->html-map]
        (render-post html-map opts)))
    id->html-map))

(defn build-index
  ([opts]
   (let [id->html-map (build-posts (assoc opts :no-output true))]
     (build-index (vals id->html-map) opts)))
  ([html-maps opts]
   (logger/log "Writing the Index Page HTML")
   (let [index-body (index/body html-maps)]
     (page/render-file {:body index-body}
                       (assoc opts
                              :skip-archive true
                              :html-filename "index.html")))))

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(defn build-atom-feed
  [opts])
