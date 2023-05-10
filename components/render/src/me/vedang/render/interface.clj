(ns me.vedang.render.interface
  (:require [me.vedang.render.core :as core]))

(defn build-posts [opts] (core/build-posts opts))
(defn build-index
  ([opts] (core/build-index opts))
  ([html-maps opts] (core/build-index html-maps opts)))
(defn build-atom-feed [opts] (core/build-atom-feed opts))
