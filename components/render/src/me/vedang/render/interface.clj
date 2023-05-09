(ns me.vedang.render.interface
  (:require [me.vedang.render.core :as core]))

(defn render-posts
  [md-dir opts]
  (core/render-posts md-dir opts))

(defn build-index
  [opts]
  (core/build-index opts))

(defn build-atom-feed
  [opts]
  (core/build-atom-feed opts))
