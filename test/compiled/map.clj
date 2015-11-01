(def demo
 (clojure.core/hash-map :a 1 :b [2] :c (clojure.core/hash-map :d 4)))

(get demo :a)

(:a demo)