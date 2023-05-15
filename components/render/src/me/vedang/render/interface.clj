(ns me.vedang.render.interface
  (:require [me.vedang.render.core :as core]))

(defn build-posts [opts] (core/build-posts opts))
(defn build-index
  ([opts] (core/build-index opts))
  ([html-maps opts] (core/build-index html-maps opts)))
(defn build-tag-pages
  ([opts] (core/build-tag-pages opts))
  ([html-maps opts] (core/build-tag-pages html-maps opts)))
(defn build-atom-feed
  ([opts] (core/build-atom-feed opts))
  ([html-maps opts] (core/build-atom-feed html-maps opts)))


(defn copy-assets
  "Copy assets into the public dir. Used to sync favicon stuff."
  [opts]
  (core/copy-assets opts))
