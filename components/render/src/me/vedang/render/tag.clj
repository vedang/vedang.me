(ns me.vedang.render.tag
  (:require
   [hiccup2.core :as hiccup]
   [me.vedang.render.util :as util]))

(defn body
  "Build the HTML body of a given Tag page"
  [html-maps page-meta]
  (let [links (util/post-links html-maps page-meta)]
    (hiccup/html [:div {:id "tag-links"
                        :class "xl:mx-32 xl:mt-8 xl:px-4 mx-16 mt-4"}
                  links])))

(defn add-html-body
  [html-maps page-meta]
  {:metadata page-meta
   :body (body html-maps page-meta)})
