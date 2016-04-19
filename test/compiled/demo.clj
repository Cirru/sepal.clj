
(println (+ 1 2 3))

(println 1 2 3 true false)

(def open-paren "(")

(defn f1 [x] (+ x 1))

(defn f2 ([a b] (+ a b)) ([a b c] (+ a b c)))

(defn- f1 [x] (+ x 1))

(println :key "string" \c \newline)

(defrecord Person [first-name last-name])
