(ns me.vedang.render.interface
  (:require [me.vedang.render.core :as core]))

(defn build-posts [opts] (core/build-posts opts))
(defn build-index [opts] (core/build-index opts))
(defn build-atom-feed [opts] (core/build-atom-feed opts))
