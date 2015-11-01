(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            [cirru.parser.core :refer [pare]]
            [clojure.string :as string]
            [cirru.sepal :refer :all]))

(defn run-make-code [file]
  (string/trim (make-code
    (pare (slurp file) ""))))

(deftest demo-test
  (testing "test demo.cirru"
    (is (=
      (string/trim (slurp "test/compiled/demo.clj"))
      (run-make-code "test/examples/demo.cirru")))))

(deftest namespace-test
  (testing "test namespace.cirru"
    (is (=
      (string/trim (slurp "test/compiled/namespace.clj"))
      (run-make-code "test/examples/namespace.cirru")))))

(deftest let-test
  (testing "test let.cirru"
    (is (=
      (string/trim (slurp "test/compiled/let.clj"))
      (run-make-code "test/examples/let.cirru")))))

(deftest comment-test
  (testing "test comment.cirru"
    (is (=
      (string/trim (slurp "test/compiled/comment.clj"))
      (run-make-code "test/examples/comment.cirru")))))
