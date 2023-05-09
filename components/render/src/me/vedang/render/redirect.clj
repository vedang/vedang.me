(ns me.vedang.render.redirect
  "Given an old-url and a new-url, construct an HTML page redirecting the
  old-url to the new-url."
  (:require
   [babashka.fs :as fs]
   [clojure.string :as cstr]
   [hiccup2.core :as hiccup]
   [me.vedang.logger.interface :as logger]))

(defn render-file
  "Given the `old-url` and the `new-url`, write out the redirect HTML page."
  [old-url new-url opts]
  ;; Assumptions:
  ;; 1. All old URLS / slugs end with a / (assuming an implicit index.html)
  ;; 2. Data written to old URL / slugs don't overwrite actual posts
  (let [redirect-html (hiccup/html
                       [:html
                        [:head
                         [:meta {:http-equiv "refresh"
                                 :content (str "0; URL=/" new-url)}]]])
        l (if (cstr/starts-with? old-url "/")
            (cstr/replace-first old-url "/" "")
            old-url)]
    (logger/log (str "Writing redirects for old urls: " l "index.html"))
    (fs/create-dirs (fs/file (:public-dir opts) l))
    (spit (fs/file (:public-dir opts) l "index.html") redirect-html)))
