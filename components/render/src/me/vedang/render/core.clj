(ns me.vedang.render.core
  (:require
   [babashka.fs :as fs]
   [clojure.data.xml :as xml]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.markdown :as markdown]
   [me.vedang.render.page :as page]
   [me.vedang.render.process :as process]
   [me.vedang.render.redirect :as redirect]))

(defn render-post
  "Given an `html-map`, write out the HTML for the post to the public folder."
  [{:keys [metadata] :as html-map} opts]
  (when (or (not (:draft metadata))
            (:publish-drafts? opts))
    (page/render-file html-map opts)
    (doseq [old-url (:aliases metadata)]
      (redirect/render-file old-url (:html-filename metadata) opts))
    ;; On success, return ID of the post. Else something above will
    ;; throw an error.
    (:id metadata)))

(defn post->html-map
  "Convert `post-file` from md format to HTML format"
  [content-dir post-file]
  (logger/log (str "Converting from Markdown to HTML: " post-file))
  (-> post-file
      slurp
      (markdown/md-to-html post-file)
      (update :metadata assoc :md-filename post-file)
      (update :metadata assoc :content-dir content-dir)))

(defn render-posts
  [opts]
  (let [html-maps (mapv (comp (partial post->html-map (:content-dir opts)) str)
                        (fs/glob (:content-dir opts) "**.md"))
        id->html-map (process/id->html-map html-maps)]
    (doall
     (keep (comp (partial render-post opts) second)
           id->html-map))))

(defn build-index
  [opts])

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(defn build-atom-feed
  [opts])
