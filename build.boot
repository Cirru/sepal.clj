
(set-env!
 :asset-paths #{}
 :source-paths #{"test/"}
 :resource-paths #{"src/"}
 :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "test"]
                 [adzerk/boot-test          "1.1.1"       :scope "test"]
                 [fipp                      "0.6.6"]
                 [cirru/parser              "0.0.3"]])

(require '[adzerk.boot-test :refer :all])

(def +version+ "0.0.14")

(task-options!
  pom {:project     'cirru/sepal
       :version     +version+
       :description "Sepal.clj -- Cirru Sepal in Clojure, indentation-based syntax for Clojure"
       :url         "https://github.com/Cirru/sepal.clj"
       :scm         {:url "https://github.com/Cirru/sepal.clj"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(set-env! :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))

(deftask build []
  (comp
   (pom)
   (jar)
   (install)
   (target)))

(deftask deploy []
  (comp
   (build)
   (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
