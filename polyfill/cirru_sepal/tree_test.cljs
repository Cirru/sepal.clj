(ns cirru-sepal.tree-test
  (:require [clojure.string :as string]
            [cljs.reader :refer [read-string]]
            [cirru-sepal.core :refer [write-code]]
            [cirru-sepal.analyze :refer [write-file]]
            [cljs.test :refer [deftest is testing run-tests]]
            ["fs" :as fs]))

(defn read-result [file]
  (fs/readFileSync (str "data/compiled/" file) "utf8"))

(defn run-make-code [file]
  (write-code
    (read-string (fs/readFileSync (str "data/examples/" file) "utf8"))))

(deftest def-test
  (testing "test def syntax"
    (is
      (=
        (read-result "def.cljs")
        (run-make-code "def.edn")))))

(deftest namespace-test
  (testing "test namespace syntax"
    (is
      (=
        (read-result "namespace.cljs")
        (run-make-code "namespace.edn")))))

(deftest let-test
  (testing "test let syntax"
    (is
      (=
        (read-result "let.cljs")
        (run-make-code "let.edn")))))

(deftest comment-test
  (testing "test comment syntax"
    (is
      (=
        (read-result "comment.cljs")
        (run-make-code "comment.edn")))))

(deftest map-test
  (testing "test map syntax"
    (is
      (=
        (read-result "map.cljs")
        (run-make-code "map.edn")))))

(deftest vector-test
  (testing "test vector syntax"
    (is
      (=
        (read-result "vector.cljs")
        (run-make-code "vector.edn")))))

(deftest fn-test
  (testing "test fn syntax"
    (is
      (=
        (read-result "fn.cljs")
        (run-make-code "fn.edn")))))

(deftest fn*-test
  (testing "test fn syntax"
    (is
      (=
        (read-result "fn*.cljs")
        (run-make-code "fn*.edn")))))

(deftest cond-test
  (testing "test cond syntax"
    (is
      (=
        (read-result "cond.cljs")
        (run-make-code "cond.edn")))))

(deftest case-test
  (testing "test case syntax"
    (is
      (=
        (read-result "case.cljs")
        (run-make-code "case.edn")))))

(deftest loop-test
  (testing "test loop syntax"
    (is
      (=
        (read-result "loop.cljs")
        (run-make-code "loop.edn")))))

(deftest doseq-test
  (testing "test doseq syntax"
    (is
      (=
        (read-result "doseq.cljs")
        (run-make-code "doseq.edn")))))

(deftest string-test
  (testing "test string syntax"
    (is
      (=
        (read-result "string.cljs")
        (run-make-code "string.edn")))))

(def example-file "
(ns a.b )

(defn main! [a b] )
")

(def example-tree {:ns ["ns" "a.b"], :proc [], :defs {:main! ["defn" "main!" ["a" "b"]]}})

(deftest file-test
  (testing "test generating file"
    (is
      (= example-file (write-file example-tree)))))

(defn main! []
  (run-tests))

(defn reload! []
  (main!))
