(ns me.vedang.render.interface
  (:require [me.vedang.render.core :as core]))

(defn build-posts [opts] (core/build-posts opts))
(defn build-index
  ([opts] (core/build-index opts))
  ([html-maps opts] (core/build-index html-maps opts)))
(defn build-atom-feed [opts] (core/build-atom-feed opts))

(defn copy-assets
  "Copy assets into the public dir. Used to sync favicon stuff."
  [opts]
  (core/copy-assets opts))
