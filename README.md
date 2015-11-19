
Cirru Sepal in Clojure
----

> Small subset of Clojure in indentation-based syntax.

```cirru
defn f1 (x)
  + x 1
```

compiles to:

```clojure
(defn f1 [x] (+ x 1))
```

Read [Quick Start][quick] if you want to compile real code.

[quick]: https://github.com/Cirru/sepal.clj/wiki/Quick-Start

Special functions:

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

Internally Sepal.clj is using `clojure.pprint/write` to generate code:

```clojure
(clojure.pprint/write quoted-code :dispatch clojure.pprint/code-dispatch)
```

### API Usage

See [lein-cirru-sepal](https://github.com/Cirru/lein-cirru-sepal/) using as a plugin.

[![](https://clojars.org/cirru/sepal/latest-version.svg)](https://clojars.org/cirru/sepal)

Also function `make-code` is exposed to transform code from Cirru syntax tree:

```clojure
(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            [cirru.parser.core :refer [pare]]
            [cirru.sepal :refer [make-code]]))

(defn run []
  (make-code
    (pare "cirru code" "filename")))
```

### Acknowledgements

* [Quoting Without Confusion](https://blog.8thlight.com/colin-jones/2012/05/22/quoting-without-confusion.html)
  showed the tricky solution for using `` `~'defn `` to remove namespace in expanding

### License

Copyright © 2015 jiyinyiyong

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
