(ns me.vedang.render.core
  (:require
   [babashka.fs :as fs]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.atom :as atom]
   [me.vedang.render.index :as index]
   [me.vedang.render.markdown :as markdown]
   [me.vedang.render.page :as page]
   [me.vedang.render.post :as post]
   [me.vedang.render.process :as process]
   [me.vedang.render.redirect :as redirect]
   [me.vedang.render.tag :as tag]))

(def create-assets
  "Return the assets directory that has been requested. Create it if it
  does not exist."
  (memoize (fn [parent-dir assets-dir-name]
             (fs/create-dirs (fs/file parent-dir assets-dir-name)))))

(defn copy-assets
  [opts]
  (let [public (create-assets (:public-dir opts) (:public-assets-dir-name opts))
        local (create-assets (:render-dir opts) (:local-assets-dir-name opts))]
    (println (str "Copying assets from: " local " to: " public))
    (fs/copy-tree local public {:replace-existing true})))

(defn post->html-map
  "Convert `post-file` from md format to HTML format"
  [content-dir post-file]
  (logger/log (str "Converting from Markdown to HTML: " post-file))
  (-> post-file
      slurp
      (markdown/md-to-html post-file)
      (update :metadata assoc :md-filename post-file)
      (update :metadata assoc :content-dir content-dir)))

(defn build-posts
  [opts]
  (let [html-maps (mapv (comp (partial post->html-map (:content-dir opts)) str)
                        (fs/glob (:content-dir opts) "**.md"))
        id->html-map (process/id->html-map html-maps)]
    (when-not (:no-output opts)
      (doseq [[_id {:keys [metadata] :as html-map}] id->html-map]
        (when (or (not (:draft metadata)) (:publish-drafts opts))
          (-> html-map
              (post/add-html-body id->html-map opts)
              (page/render-file opts))
          (doseq [old-url (:aliases metadata)]
            (redirect/render-file old-url (:html-filename metadata) opts)))))
    id->html-map))

(defn build-index
  ([opts]
   (let [id->html-map (build-posts (assoc opts :no-output true))]
     (build-index (vals id->html-map) opts)))
  ([html-maps opts]
   (logger/log "Writing the Index Page HTML")
   (-> html-maps
       process/sort-and-filter
       (index/add-html-body {:skip-archive true
                             :html-filename "index.html"})
       (page/render-file opts))))

(defn build-tag-pages
  ([opts]
   (let [id->html-map (build-posts (assoc opts :no-output true))]
     (build-tag-pages (vals id->html-map) opts)))
  ([html-maps opts]
   (logger/log "Writing Tag pages HTML")
   (doseq [[tag posts] (-> html-maps
                           process/sort-and-filter
                           process/tag->html-map)]
     (-> posts
         (tag/add-html-body {:html-filename (str "tags/" tag ".html")
                             :title (str "Posts tagged with: " tag)})
         (page/render-file opts)))))

(defn build-atom-feed
  ([opts]
   (let [id->html-map (build-posts (assoc opts :no-output true))]
     (build-atom-feed (vals id->html-map) opts)))
  ([html-maps opts]
   (let [html-maps (process/sort-and-filter html-maps)]
     (logger/log "Writing the Atom feeds for the site")
     ;; notes are my brain-forest entries of stuff I read / watch. I
     ;; don't need to publish them in the feed.
     (atom/feed (remove (fn [{:keys [metadata]}]
                          (some (set (:categories metadata)) ["notes"]))
                        html-maps)
                (assoc opts :xml-filename "atom.xml"))
     ;; Build a feed specifically for Planet Clojure
     (atom/feed (filter (fn [{:keys [metadata]}]
                          (some (:tags metadata) ["clojure" "clojurescript"]))
                        html-maps)
                (assoc opts :xml-filename "planetclojure.xml")))))
