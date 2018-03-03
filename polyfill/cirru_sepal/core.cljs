
(ns cirru-sepal.core
  (:require [clojure.string :as string]
            [fipp.clojure :as fipp]
            [cljs.reader :refer [read-string]]))

(declare transform-xs)
(declare transform-x)

; file demo

(defn transform-apply [xs]
  (map transform-x xs))

(defn transform-defn [func params & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (coll? params) "[Sepal] params should be a sequence!")
  `(~'defn ~(symbol func) [~@(map transform-x params)] ~@(map transform-x body)))

(defn transform-defn$ [func & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (every? coll? body) "[Sepal] body should be deep colls!")
  `(~'defn ~(symbol func)
      ~@(map
          (fn [definition]
            (let [[sub-params & sub-body] definition]
              `([~@(map transform-x sub-params)] ~@(map transform-x sub-body))))
      body)))

(defn transform-defn- [func params & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (coll? params) "[Sepal] params should be a sequence!")
  `(~'defn- ~(symbol func) [~@(map transform-x params)] ~@(map transform-x body)))

(defn transform-defn$- [func & body]
  (assert (string? func) "[Sepal] function name should be a symbol!")
  (assert (every? coll? body) "[Sepal] body should be nested colls!")
  `(~'defn- ~(symbol func)
      ~@(map
          (fn [definition]
            (let [[sub-params & sub-body] definition]
              `([~@(map transform-x sub-params)] ~@(map transform-x sub-body))))
      body)))

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
  (assert (coll? pairs) (str "[Sepal] let requires a sequence for bindings: " pairs))
  (assert (every? coll? pairs) (str "[Sepal] detected literal in let bindings: " pairs))
  `(~'let [~@(map transform-x (apply concat pairs))] ~@(map transform-x body)))

; file loop

(defn transform-loop [pairs & body]
  (assert (coll? pairs) (str "[Sepal] loop requires a sequence for bindings: " pairs))
  (assert (every? coll? pairs) (str "[Sepal] detected literal in loop bindings: " pairs))
  `(~'loop [~@(map transform-x (apply concat pairs))] ~@(map transform-x body)))

; file doseq

(defn transform-doseq [pairs & body]
  (assert (coll? pairs) (str "[Sepal] doseq requires a sequence for bindings: " pairs))
  (assert (every? coll? pairs) (str "[Sepal] detected literal in doseq bindings: " pairs))
  `(~'doseq [~@(map transform-x (apply concat pairs))] ~@(map transform-x body)))

; file comment

(defn transform-comment [& body]
  `(~'comment ~@(map transform-x body)))

; file fn
(defn transform-fn [params & body]
  (assert (coll? params) "[Sepal] params for fn should be a sequence!")
  `(~'fn [~@(map transform-x params)] ~@(map transform-x body)))

; file fn$
(defn transform-fn$ [& body]
  (assert (every? coll? body) "[Sepal] body should be nested colls!")
  `(~'fn
    ~@(map
        (fn [definition]
          (let [[sub-params & sub-body] definition]
            `([~@(map transform-x sub-params)] ~@(map transform-x sub-body))))
      body)))

; file fn*
(defn transform-fn* [& body]
  `(~'fn* [] ~(map transform-x body)))

; file cond
(defn transform-cond [& body]
  `(~'cond ~@(map transform-x (apply concat body))))

; file case
(defn transform-case [condition & body]
  `(~'case ~(transform-x condition)
      ~@(map transform-x (apply concat (butlast body)))
      ~(transform-x (last body))))

(defn transform-token [x] (symbol x))

(defn transform-x [x]
  (cond
    (string? x)
    (cond
      (= x "true") true
      (= x "false") false
      (= x "nil") nil
      (= (first x) \:) (keyword (subs x 1))
      (= (first x) \|) (subs x 1)
      (= (first x) \") (subs x 1)
      (= (first x) \') `(quote ~(symbol (subs x 1)))
      (= (first x) \\) (read-string x)
      (re-matches #"-?\d+(\.\d+)?" x) (read-string x)
      :else (symbol x))
    (vector? x)
    (do
      (transform-xs x))
    :else (str "unknown:" x)))

(defn transform-xs
  ([] [])
  ([xs]
   (case (first xs)
    ; demo
    "def" (apply transform-def (rest xs))
    "defn" (apply transform-defn (rest xs))
    "defn$" (apply transform-defn$ (rest xs))
    "defn-" (apply transform-defn- (rest xs))
    "defn$-" (apply transform-defn$- (rest xs))
    "defrecord" (apply transform-defrecord (rest xs))
    "[]" (apply transform-vector (rest xs))
    "{}" (apply transform-hashmap (rest xs))
    "#{}" (apply transform-hashset (rest xs))
    "'()" (apply transform-list (rest xs))
    ; namespace
    "ns" (apply transform-ns (rest xs))
    ":require" (apply transform-require (rest xs))
    ":use" (apply transform-use (rest xs))
    ; let
    "let" (apply transform-let (rest xs))
    ; loop
    "loop" (apply transform-loop (rest xs))
    ; doseq
    "doseq" (apply transform-doseq (rest xs))
    ; comment
    ";" (apply transform-comment (rest xs))
    ";;" (apply transform-comment (rest xs))
    ; fn
    "fn" (apply transform-fn (rest xs))
    "fn$" (apply transform-fn$ (rest xs))
    ; fn*
    "#()" (apply transform-fn* (rest xs))
    "fn*" (apply transform-fn* (rest xs))
    ; cond
    "cond" (apply transform-cond (rest xs))
    ; case
    "case" (apply transform-case (rest xs))
    (transform-apply xs))))


(defn make-string [xs]
  (let
    [result (transform-xs xs)]
    (string/trim (with-out-str (fipp/pprint result {:width 92})))))

(defn make-line [xs]
  (str "\n" (make-string xs) "\n"))

(defn write-code [xs]
  (string/join ""
    (map make-line xs)))
