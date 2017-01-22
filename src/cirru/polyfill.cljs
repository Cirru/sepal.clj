
(ns cirru.polyfill
  (:require [cljs.reader :refer [read-string]]))

(def read-string* read-string)
