(ns cirru.sepal
  (:require
    [clojure.pprint :as p]
    [clojure.string :as string]))

(declare transform-xs)

(defn make-line [xs]
  (str "\n" (make-string xs)))

(defn make-code [xs]
  (string/join "\n"
    (map make-line xs)))

(defn make-string [xs]
  (with-out-str
    (p/write (transform-xs xs) :dispatch p/code-dispatch)))

(defn transform-expr [])

(defn transform-apply [xs]
  (map transform-x xs))

(defn transform-if [])

(defn transform-defn [])

(defn transform-xs
  ([] nil)
  ([xs]
    (case (first xs)
      "defn" (transform-defn (rest xs))
      (transform-apply xs))))

(defn transform-token [x] (symbol x))

(defn transform-x [x]
  (cond
    (string? x) (transform-token x)
    (vector? x) (do
      (transform-xs x))
    :else "unknown"))
