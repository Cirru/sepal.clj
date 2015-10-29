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
      (string/trim (slurp "compiled/demo.clj"))
      (run-make-code "examples/demo.cirru")))))

(deftest namespace-test
  (testing "test namespace.cirru"
    (is (=
      (string/trim (slurp "compiled/namespace.clj"))
      (run-make-code "examples/namespace.cirru")))))
