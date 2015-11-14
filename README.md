
Cirru Sepal in Clojure
----

> Small subset of Clojure in indentation-based syntax.

```cirru
defn f1 (x)
  + x 1
```

compiles to:

```clojure
(clojure.core/defn f1 [x] (+ x 1))
```

Currently handled functions:

* `[]`
* `{}`
* `--` for comment
* `case`
* `def`
* `defn`
* `defn-`
* `fn`
* `let`
* `ns`

Read `test/examples/` and `test/compiled/` for details.

### Usage

See [lein-cirru-sepal](https://github.com/Cirru/lein-cirru-sepal/) using as a plugin.

[![](https://clojars.org/cirru/sepal/latest-version.svg)](https://clojars.org/cirru/sepal)

Also function(`make-code`) for transforming code is available:

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

### Acknowledgements

* [Quoting Without Confusion](https://blog.8thlight.com/colin-jones/2012/05/22/quoting-without-confusion.html)
  showed the tricky solution for using `` `~'defn `` to remove namespace in expanding

### License

Copyright © 2015 jiyinyiyong

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
