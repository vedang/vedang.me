(ns me.vedang.render.markdown
  "Given markdown files, convert them to an `html-map` object.

  The `html-map` object contains the following keys:
  `:html`: The content of the file converted from markdown to HTML
  `:metadata`: The metadata associated with the file, as it's front-matter."
  (:require [clojure.string :as cstr]
            [markdown.core :as md]
            [me.vedang.logger.interface :as logger]))

(defn pre-process-markdown
  "Process the markdown before converting it to HTML. This is a
workaround for https://github.com/yogthos/markdown-clj/issues/146"
  [markdown]
  (-> markdown
      ;; Replace -- with a temp value to ensure `md-to-html-string`
      ;; does not mess with it.
      (cstr/replace #" -- " (fn [_] "$$NDASH$$"))
      ;; allow links with markup over multiple lines
      (cstr/replace #"\[[^\]]+\n"
                    (fn [match] (cstr/replace match "\n" "$$RET$$")))))

(defn post-process-html
  "Run the generated HTML through some post-processing. This is a
  workaround for https://github.com/yogthos/markdown-clj/issues/146"
  [html-map]
  (-> html-map
      ;; Get back our -- and newlines, undoing `pre-process-markdown`
      (update :html cstr/replace "$$NDASH$$" " -- ")
      (update :html cstr/replace "$$RET$$" "\n")))

(defn md-to-html
  "Take a string of markdown, slurped from a post file, and convert it to
  HTML. Handle the case of the actual file-contents being empty, and
  only a stub-entry existing.

  Return a data-map of the post."
  [markdown post-filename]
  (let [pre-process (pre-process-markdown markdown)]
    (post-process-html
     (try (md/md-to-html-string-with-meta
           pre-process
           :code-style (fn [lang] (format "class=\"lang-%s\"" lang))
           :footnotes? true
           :heading-anchors true)
          (catch java.lang.NullPointerException _
            (logger/log (str "-- Looks like " post-filename " is empty!"))
            (if-let [m (md/md-to-meta markdown)]
              {:metadata m :html "" :stub? true}
              ;; Looks like the whole file is empty.
              (throw (ex-info "Encountered an empty file."
                              {:md-filename post-filename}))))))))
