(ns cirru.sepal-test
  (:require [clojure.string :as string]
            [cljs.reader :refer [read-string]]
            [cirru.sepal :refer [make-code]]
            [cljs.test :refer [deftest is testing run-tests]]
            ["fs" :as fs]))

(defn run-make-code [file]
  (make-code
    (read-string (fs/readFileSync file "utf8"))))

(deftest def-test
  (testing "test def.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/def.cljs" "utf8")
        (run-make-code "data/examples/def.edn")))))

(deftest namespace-test
  (testing "test namespace.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/namespace.cljs" "utf8")
        (run-make-code "data/examples/namespace.edn")))))

(deftest let-test
  (testing "test let.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/let.cljs" "utf8")
        (run-make-code "data/examples/let.edn")))))

(deftest comment-test
  (testing "test comment.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/comment.cljs" "utf8")
        (run-make-code "data/examples/comment.edn")))))

(deftest map-test
  (testing "test map.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/map.cljs" "utf8")
        (run-make-code "data/examples/map.edn")))))

(deftest vector-test
  (testing "test vector.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/vector.cljs" "utf8")
        (run-make-code "data/examples/vector.edn")))))

(deftest fn-test
  (testing "test fn.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/fn.cljs" "utf8")
        (run-make-code "data/examples/fn.edn")))))

(deftest fn-test
  (testing "test fn.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/fn*.cljs" "utf8")
        (run-make-code "data/examples/fn*.edn")))))

(deftest cond-test
  (testing "test cond.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/cond.cljs" "utf8")
        (run-make-code "data/examples/cond.edn")))))

(deftest case-test
  (testing "test case.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/case.cljs" "utf8")
        (run-make-code "data/examples/case.edn")))))


(deftest loop-test
  (testing "test case.cirru"
    (is
      (=
        (fs/readFileSync "data/compiled/loop.cljs" "utf8")
        (run-make-code "data/examples/loop.edn")))))


(defn main! []
  (run-tests))

(defn reload! []
  (main!))
