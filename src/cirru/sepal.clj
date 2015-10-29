(ns cirru.sepal
  (:require
    [clojure.pprint :as p]
    [clojure.string :as string]))

(declare transform-xs)
(declare transform-x)

(defn make-string [xs]
  (with-out-str
    (p/write (transform-xs xs))))

(defn make-line [xs]
  (str "\n" (make-string xs)))

(defn make-code [xs]
  (string/join "\n"
    (map make-line xs)))

(defn transform-expr [])

(defn transform-apply [xs]
  (map transform-x xs))

(defn transform-if [])

(defn transform-defn [func params & body]
  `(defn ~(symbol func) [~@(map symbol params)] ~@(map transform-x body)))

(defn transform-vector [& body]
  `[~@(map transform-x body)])

(defn transform-hashmap [& body]
  `(hash-map ~@(map transform-x (apply concat body))))

(defn transform-ns [& body]
  `(ns ~@(map transform-x body)))

(defn transform-xs
  ([] [])
  ([xs]
    (case (first xs)
      "defn" (apply transform-defn (rest xs))
      "[]" (apply transform-vector (rest xs))
      "{}" (apply transform-hashmap (rest xs))
      "ns" (apply transform-ns (rest xs))
      (transform-apply xs))))

(defn transform-token [x] (symbol x))

(defn transform-x [x]
  (cond
    (string? x) (cond
      (= (first x) \:) (keyword (subs x 1))
      (= (first x) \|) (subs x 1)
      (re-matches #"-?\d+(\.\d+)?" x) (load-string x)
      (= (first x) \\) (load-string x)
      :else (symbol x))
    (vector? x) (do
      (transform-xs x))
    :else (str "unknown:" x)))
