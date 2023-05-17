(ns me.vedang.weblog.core
  (:require
   [me.vedang.content.interface :as content]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.interface :as render]))

(defn build-site
  [opts]
  (when (:log-level opts) (logger/set-log-level! (:log-level opts)))
  (logger/log (str "Options Map: " opts))
  (content/copy-assets opts)
  (render/copy-assets opts)
  (let [id->html-map (render/build-posts opts)
        html-maps (vals id->html-map)]
    (render/build-index html-maps opts)
    (render/build-tag-pages html-maps opts)
    (render/build-atom-feed html-maps opts)))
