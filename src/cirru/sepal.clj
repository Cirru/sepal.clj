(ns cirru.sepal
  (:require
    [clojure.pprint :as p]
    [clojure.string :as string]))

(declare transform-xs)
(declare transform-x)

(defn make-string [xs]
  (let
    [result (transform-xs xs)]
    (with-out-str
      (p/write result :dispatch clojure.pprint/code-dispatch))))

(defn make-line [xs]
  (str "\n" (make-string xs) "\n"))

(defn make-code [xs]
  (string/join ""
    (map make-line xs)))

; file demo

(defn transform-apply [xs]
  (map transform-x xs))

(defn transform-defn [func params & body]
  `(~'defn ~(symbol func) [~@(map symbol params)] ~@(map transform-x body)))

(defn transform-defn- [func params & body]
  `(~'defn- ~(symbol func) [~@(map symbol params)] ~@(map transform-x body)))

(defn transform-def [& body]
  `(~'def ~@(map transform-x body)))

(defn transform-vector [& body]
  `[~@(map transform-x body)])

(defn transform-hashmap [& body]
  (if (every? coll? body)
    `{~@(map transform-x (apply concat body)) ~@(list)}
    `{~@(map transform-x body) ~@(list)}))

; file namespace

(defn transform-ns [& body]
  `(~'ns ~@(map transform-x body)))

(defn transform-require [& body]
  `(:require ~@(map transform-x body)))

(defn transform-use [& body]
  `(:use ~@(map transform-x body)))

; file let

(defn transform-let [pairs & body]
  `(~'let [~@(map transform-x (apply concat pairs))] ~@(map transform-x body)))

; file comment

(defn transform-comment [& body]
  `(~'comment ~@(map transform-x body)))

; file fn

(defn transform-fn [params & body]
  `(~'fn [~@(map symbol params)] ~@(map transform-x body)))

; file cond
(defn transform-cond [& body]
  `(~'cond ~@(map transform-x (apply concat body))))

; file case
(defn transform-case [condition & body]
  `(~'case ~(transform-x condition)
      ~@(map transform-x (apply concat (butlast body)))
      ~(transform-x (last body))))

(defn transform-xs
  ([] [])
  ([xs]
    (case (first xs)
      ; demo
      "def" (apply transform-def (rest xs))
      "defn" (apply transform-defn (rest xs))
      "defn-" (apply transform-defn- (rest xs))
      "[]" (apply transform-vector (rest xs))
      "{}" (apply transform-hashmap (rest xs))
      ; namespace
      "ns" (apply transform-ns (rest xs))
      ":require" (apply transform-require (rest xs))
      ":use" (apply transform-use (rest xs))
      ; let
      "let" (apply transform-let (rest xs))
      ; comment
      "--" (apply transform-comment (rest xs))
      ; fn
      "fn" (apply transform-fn (rest xs))
      ; cond
      "cond" (apply transform-cond (rest xs))
      ; case
      "case" (apply transform-case (rest xs))
      (transform-apply xs))))

(defn transform-token [x] (symbol x))

(defn transform-x [x]
  (cond
    (string? x) (cond
      (= x "true") true
      (= x "false") false
      (= (first x) \:) (keyword (subs x 1))
      (= (first x) \|) (subs x 1)
      (= (first x) \') `(quote ~(symbol (subs x 1)))
      (re-matches #"-?\d+(\.\d+)?" x) (load-string x)
      (= (first x) \\) (load-string x)
      :else (symbol x))
    (vector? x) (do
      (transform-xs x))
    :else (str "unknown:" x)))
