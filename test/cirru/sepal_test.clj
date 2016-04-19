(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            [cirru.parser.core :refer [pare]]
            [clojure.string :as string]
            [cirru.sepal :refer :all]))

(defn run-make-code [file]
  (make-code
    (pare (slurp file) "")))

(deftest def-test
  (testing "test def.cirru"
    (is (=
      (slurp "test/compiled/def.clj")
      (run-make-code "test/examples/def.cirru")))))

(deftest namespace-test
  (testing "test namespace.cirru"
    (is (=
      (slurp "test/compiled/namespace.clj")
      (run-make-code "test/examples/namespace.cirru")))))

(deftest let-test
  (testing "test let.cirru"
    (is (=
      (slurp "test/compiled/let.clj")
      (run-make-code "test/examples/let.cirru")))))

(deftest comment-test
  (testing "test comment.cirru"
    (is (=
      (slurp "test/compiled/comment.clj")
      (run-make-code "test/examples/comment.cirru")))))

(deftest map-test
  (testing "test map.cirru"
    (is (=
      (slurp "test/compiled/map.clj")
      (run-make-code "test/examples/map.cirru")))))

(deftest vector-test
  (testing "test vector.cirru"
    (is (=
      (slurp "test/compiled/vector.clj")
      (run-make-code "test/examples/vector.cirru")))))

(deftest fn-test
  (testing "test fn.cirru"
    (is (=
      (slurp "test/compiled/fn.clj")
      (run-make-code "test/examples/fn.cirru")))))

(deftest cond-test
  (testing "test cond.cirru"
    (is (=
      (slurp "test/compiled/cond.clj")
      (run-make-code "test/examples/cond.cirru")))))

(deftest case-test
  (testing "test case.cirru"
    (is (=
      (slurp "test/compiled/case.clj")
      (run-make-code "test/examples/case.cirru")))))
