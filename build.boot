
(defn read-password [guide]
  (String/valueOf (.readPassword (System/console) guide nil)))

(set-env!
  :resource-paths #{"src" "polyfill"}
  :dependencies '[[fipp "0.6.12"]]
  :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"
                                     :username "jiyinyiyong"
                                     :password (read-password "Clojars password: ")}]))

(def +version+ "0.2.0")

(deftask deploy []
  (comp
    (pom :project     'cirru/sepal
         :version     +version+
         :description "Generate Clojure code from syntax tree"
         :url         "https://github.com/Cirru/sepal.clj"
         :scm         {:url "https://github.com/Cirru/sepal.clj"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (push :repo "clojars" :gpg-sign false)))
