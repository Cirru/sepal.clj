(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            [cirru.parser.core :refer [pare]]
            [clojure.string :as string]
            [cirru.sepal :refer :all]))

(deftest demo-test
  (testing "test demo.cirru"
    (is (=
      (string/trim (slurp "compiled/demo.clj"))
      (string/trim (make-code
        (pare (slurp "examples/demo.cirru") "")))))))
