(ns me.vedang.render.index
  (:require
   [clojure.string :as cstr]
   [hiccup2.core :as hiccup]
   [me.vedang.render.util :as util]))

(defn body
  "Build the HTML body of the Index page"
  [html-maps page-meta]
  (let [groups (group-by #(or (first (get-in % [:metadata :categories]))
                              "not categorised")
                         html-maps)
        cat->post-links (reduce-kv
                         (fn [m category posts]
                           (assoc m
                                  category (util/post-links posts
                                                       (assoc page-meta
                                                              :title (cstr/capitalize (or category "Archive"))))))
                         {}
                         groups)]
    (hiccup/html [:div {:id "index-links"
                        :class " mx-8 mt-2 xl:mx-32 xl:mt-8 xl:px-4"}
                  (get cat->post-links "programming")
                  (get cat->post-links "tools")
                  (get cat->post-links "tinylog")
                  (get cat->post-links "organization")
                  (get cat->post-links "interviews")
                  (get cat->post-links "notes")
                  (get cat->post-links "projects")
                  (get cat->post-links "mahabharata")
                  (get cat->post-links "random")])))

(defn add-html-body
  [html-maps page-meta]
  {:metadata page-meta
   :body (body html-maps page-meta)})
