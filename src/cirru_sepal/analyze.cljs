
(ns cirru-sepal.analyze
  (:require [clojure.string :as string] [cirru-sepal.core :refer [write-code]]))

(defn depends-on? [x y dict level]
  (if (contains? dict x)
    (let [deps (:tokens (get dict x))]
      (if (contains? deps y)
        true
        (if (> level 4)
          false
          (some (fn [child] (depends-on? child y dict (inc level))) deps))))
    false))

(def def-names #{"def" "defonce"})

(defn deps-insert [acc new-item items deps-info]
  (if (empty? items)
    (conj acc new-item)
    (let [cursor (first items)]
      (if (depends-on? cursor new-item deps-info 0)
        (if (depends-on? new-item cursor deps-info 0)
          (if (contains? def-names (:kind (get deps-info new-item)))
            (recur (conj acc cursor) new-item (rest items) deps-info)
            (into [] (concat acc [new-item] items)))
          (into [] (concat acc [new-item] items)))
        (recur (conj acc cursor) new-item (rest items) deps-info)))))

(def files-cache-ref (atom {}))

(defn strip-property [x] (if (string/includes? x ".") (first (string/split x ".")) x))

(defn strip-atom [token] (if (string/starts-with? token "@") (subs token 1) token))

(defn deps-sort [acc items deps-info]
  (if (empty? items)
    acc
    (let [cursor (first items), next-acc (deps-insert [] cursor acc deps-info)]
      (recur next-acc (into [] (rest items)) deps-info))))

(defn write-file [file-info]
  (let [ns-line (:ns file-info)
        definitions (:defs file-info)
        proc (or (:proc file-info) (:procs file-info))
        var-names (into #{} (keys definitions))
        deps-info (->> definitions
                       (map
                        (fn [entry]
                          (comment println "Handling definitons:" entry)
                          (let [var-name (first entry)
                                tree (last entry)
                                dep-tokens (->> (subvec tree 2)
                                                (flatten)
                                                (distinct)
                                                (map strip-atom)
                                                (map strip-property)
                                                (filter
                                                 (fn [token]
                                                   (and (contains? var-names token)
                                                        (not= token var-name))))
                                                (into (hash-set)))]
                            [var-name {:kind (first tree), :tokens dep-tokens}])))
                       (into {}))
        self-deps-names (filter (fn [x] (depends-on? x x deps-info 0)) var-names)
        sorted-names (deps-sort [] (sort var-names) deps-info)
        declarations (->> self-deps-names
                          (map (fn [var-name] ["declare" var-name]))
                          (into []))
        definition-lines (map (fn [var-name] (get definitions var-name)) sorted-names)
        tree (into [] (concat [ns-line] declarations definition-lines proc))
        code (write-code tree)]
    (comment println "before sort:" var-names)
    (comment println "after  sort:" sorted-names)
    (comment println "generated file:" code)
    code))

(defn ns->path [pkg ns-part]
  (-> (str pkg "." ns-part)
      (string/replace (re-pattern "\\.") "/")
      (string/replace (re-pattern "-") "_")))
