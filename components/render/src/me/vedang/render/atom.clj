(ns me.vedang.render.atom
  (:require
   [babashka.fs :as fs]
   [cljc.java-time.format.date-time-formatter :as cjtfdf]
   [cljc.java-time.zone-id :as cjtzi]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.data.xml :as xml]
   [me.vedang.logger.interface :as logger]))

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(let [rfc-3339-pat "yyyy-MM-dd'T'HH:mm:ssxxx"]
  (defn atom-xml
    ;; validate at https://validator.w3.org/feed/check.cgi
    [html-maps opts]
    (-> [::atom/feed
         {:xmlns "http://www.w3.org/2005/Atom"}
         [::atom/title (:blog-title opts)]
         [::atom/link {:href (str (:blog-root opts) (:xml-filename opts)) :rel "self"}]
         [::atom/link {:href (:blog-root opts)}]
         [::atom/updated (cjtz/format (cjtz/now)
                                      (cjtfdf/of-pattern rfc-3339-pat))]
         [::atom/id (:blog-root opts)]
         [::atom/author [::atom/name (:blog-author opts)]]
         (for [{{:keys [title date html-filename]} :metadata
                html :html}
               html-maps]
           [::atom/entry
            [::atom/id html-filename]
            [::atom/link {:href html-filename}]
            [::atom/title title]
            [::atom/updated (cjtz/format (cjtz/of-instant date (cjtzi/of "UTC"))
                                         (cjtfdf/of-pattern rfc-3339-pat))]
            [::atom/content {:type "html"} [:-cdata html]]])]
        xml/sexp-as-element
        xml/indent-str)))

(defn feed
  "Build an atom feed of all the provided `html-maps`."
  [html-maps opts]
  (let [xml-str (atom-xml html-maps opts)
        xml-file (fs/file (:public-dir opts) (:xml-filename opts))]
    (logger/log (str "Writing XML to public-dir: " xml-file))
    (fs/create-dirs (fs/parent xml-file))
    (spit xml-file xml-str)))
