
Sepal.clj
----

Cirru Sepal experiment in Clojure.

> Small subset of Clojure in indentation-based syntax.

Read `test/examples/` and `test/compiled/` for details.

### Usage

[![](https://clojars.org/cirru/sepal/latest-version.svg)](https://clojars.org/cirru/sepal)

```clojure
(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            [cirru.parser.core :refer [pare]]
            [clojure.string :as string]
            [cirru.sepal :refer :all]))

(defn run-make-code []
  (string/trim (make-code
    (pare (slurp "examples/demo.cirru") ""))))
```

### License

Copyright © 2015 jiyinyiyong

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
