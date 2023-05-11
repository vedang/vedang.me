(ns me.vedang.weblog.core
  (:require
   [me.vedang.content.interface :as content]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.interface :as render]))

(defn build-site
  [opts]
  (when (:print-logs opts) (logger/activate-logging!))
  (logger/log (str "Options Map: " opts))
  (content/copy-assets opts)
  (render/copy-assets opts)
  (let [id->html-map (render/build-posts opts)]
    (render/build-index (vals id->html-map) opts)
    (render/build-atom-feed opts)))
