(ns me.vedang.content.interface
  (:require [me.vedang.content.core :as core]))

(defn copy-assets!
  "Copy assets into the public dir. Used to sync images and CSS."
  [opts]
  (core/copy-assets! opts))

;;; The code that will eventually go here will be the ability to drop
;;; into the actually org-mode files and generate the markdown output
;;; by running Emacs + Ox-Neuron.
