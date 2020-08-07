
(println (+ 1 2 3))

(println 1 2 3 true false)

(def open-paren "(")

(def dev? ^boolean js/goog.DEBUG)

(defn f1 [x] (+ x 1))

(defn f2 ([a b] (+ a b)) ([a b c] (+ a b c)))

(defn ^:dev/after-load f3 [])

(defn- f1 [x] (+ x 1))

(println :key "string" "c" "\n")
