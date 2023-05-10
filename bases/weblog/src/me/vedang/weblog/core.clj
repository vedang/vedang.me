(ns me.vedang.weblog.core
  (:require
   [me.vedang.content.interface :as content]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.interface :as render]))

(defn build-site
  [opts]
  (when (:print-logs? opts) (logger/activate-logging!))
  (content/copy-assets opts)
  (render/build-posts opts)
  (render/build-index opts)
  (render/build-atom-feed opts))
