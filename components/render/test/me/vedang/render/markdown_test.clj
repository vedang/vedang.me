(ns me.vedang.render.markdown-test
  (:require
   [babashka.fs :as fs]
   [clojure.test :as t]
   [me.vedang.render.markdown :as sut]))

(def file-with-content
  (slurp
   (fs/file "components/render/resources/render/metadata_and_content.md")))
(def file-without-content
  (slurp
   (fs/file "components/render/resources/render/only_metadata.md")))

(t/deftest process-handles-markdown-metadata
  (let [with-content (sut/md-to-html file-with-content
                                     "metadata_and_content.md")
        without-content (sut/md-to-html file-without-content
                                        "only_metadata.md")]
    (t/is (seq (:html with-content)))
    (t/is (seq (:metadata with-content)))

    (t/is (not (seq (:html without-content))))
    (t/is (seq (:metadata without-content)))))
