(ns cirru.sepal-test
  (:require [clojure.string :as string]
            [cljs.reader :refer [read-string]]
            [cirru.sepal :refer [make-code]]
            [cljs.test :refer [deftest is testing run-tests]]
            ["fs" :as fs]))

(defn read-result [file]
  (fs/readFileSync (str "data/compiled/" file) "utf8"))

(defn run-make-code [file]
  (make-code
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
  (testing "test case syntax"
    (is
      (=
        (read-result "loop.cljs")
        (run-make-code "loop.edn")))))


(defn main! []
  (run-tests))

(defn reload! []
  (main!))
