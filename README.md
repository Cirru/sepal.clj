
Cirru Sepal for Clojure
----

> Generate Clojure code from syntax tree

```json
[ "defn" "f1" [ "x" ]
  [ "+" "x" "1" ] ]
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
* `#{}`
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

[![Clojars Project](https://img.shields.io/clojars/v/cirru/sepal.svg)](https://clojars.org/cirru/sepal)

```clojure
[cirru/sepal "0.0.13"]
```

See [boot-cirru-sepal](https://github.com/Cirru/boot-cirru-sepal) using as a plugin.

Also function `make-code` is exposed to transform code from Cirru syntax tree:

```clojure
(ns cirru.sepal-test
  (:require [clojure.test :refer :all]
            ; cirru is an old indentation based text syntax
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

MIT
