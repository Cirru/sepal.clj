
(ns cirru.sepal
  (:require
    [fipp.clojure   :as fipp]
    [clojure.string :as string]))

; file demo

(defn transform-apply [xs]
  (map transform-x xs))

(defn transform-defn [func params & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (coll? params) "[Sepal] params should be a sequence!")
  (if (vector? (first params))
    (let [all-body (conj body params)]
      `(~'defn ~(symbol func) ~@(map
        (fn [definition]
          (let [[sub-params & sub-body] definition]
            `([~@(map symbol sub-params)] ~@(map transform-x sub-body))))
        all-body)))
    `(~'defn ~(symbol func) [~@(map symbol params)] ~@(map transform-x body))))

(defn transform-defn- [func params & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (coll? params) "[Sepal] params should be a sequence!")
  (if (vector? (first params))
    (let [all-body (conj body params)]
      `(~'defn- ~(symbol func) ~@(map
        (fn [definition]
          (let [[sub-params & sub-body] definition]
            `([~@(map symbol sub-params)] ~@(map transform-x sub-body))))
        all-body)))
    `(~'defn- ~(symbol func) [~@(map symbol params)] ~@(map transform-x body))))

(defn transform-def [var-name body]
  (assert (string? var-name) "[Sepal] variable name should be a symbol!")
  (assert (some? body) (str "[Sepal] value for " var-name " is required!"))
  `(~'def ~(symbol var-name) ~(transform-x body)))

(defn transform-defrecord [record-name fields]
  `(~'defrecord ~(symbol record-name) [~@(map symbol fields)]))

(defn transform-vector [& body]
  `[~@(map transform-x body)])

(defn transform-list [& body]
  `'(~@(map transform-x body)))

(defn transform-hashmap [& body]
  (if (every? coll? body)
    `{~@(map transform-x (apply concat body)) ~@(list)}
    `{~@(map transform-x body) ~@(list)}))

(defn transform-hashset [& body]
  `#{~@(map transform-x body)})

; file namespace

(defn transform-ns [& body]
  `(~'ns ~@(map transform-x body)))

(defn transform-require [& body]
  `(:require ~@(map transform-x body)))

(defn transform-use [& body]
  `(:use ~@(map transform-x body)))

; file let

(defn transform-let [pairs & body]
  (assert (coll? pairs) "[Sepal] let requires a sequence for bindings!")
  `(~'let [~@(map transform-x (apply concat pairs))] ~@(map transform-x body)))

; file comment

(defn transform-comment [& body]
  `(~'comment ~@(map transform-x body)))

; file fn
(defn transform-fn [params & body]
  (assert (coll? params) "[Sepal] params for fn should be a sequence!")
  (if (vector? (first params))
    (let [all-body (conj body params)]
      `(~'fn ~@(map
        (fn [definition]
          (let [[sub-params & sub-body] definition]
            `([~@(map symbol sub-params)] ~@(map transform-x sub-body))))
        all-body)))
    `(~'fn [~@(map symbol params)] ~@(map transform-x body))))

; file cond
(defn transform-cond [& body]
  `(~'cond ~@(map transform-x (apply concat body))))

; file case
(defn transform-case [condition & body]
  `(~'case ~(transform-x condition)
      ~@(map transform-x (apply concat (butlast body)))
      ~(transform-x (last body))))
