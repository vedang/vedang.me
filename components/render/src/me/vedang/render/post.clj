(ns me.vedang.render.post
  (:require
   [clojure.string :as cstr]
   [me.vedang.render.util :as util]
   [selmer.parser :as selmer]))

(defn add-html-body
  [{:keys [metadata html] :as html-map} opts]
  (let [post-opts (cond-> opts
                    true (merge metadata)

                    (seq (str html)) (assoc :body html)

                    (seq (:parents metadata))
                    (assoc :parents_str (cstr/join ", " (:parents metadata)))

                    (seq (:children metadata))
                    (assoc :children_str (cstr/join ", " (:children metadata)))

                    (seq (:tags metadata))
                    (assoc :tags_str (cstr/join ", " (:tags metadata))))]
    (assoc html-map :body (-> post-opts
                              :templates-dir
                              (util/read-template "post.html")
                              (selmer/render post-opts)))))
