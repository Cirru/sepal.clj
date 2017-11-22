
Cirru Sepal for Clojure
----

> Generate Clojure code from syntax tree

```json
[ "defn" "f1" [ "x" ]
  [ "+" "x" "1" ] ]
```

```clojure
(defn f1 [x] (+ x 1))
```

Read [Quick Start](https://github.com/Cirru/sepal.clj/wiki/Quick-Start) if you want to compile real code. It's based on [fipp](https://github.com/brandonbloom/fipp/) for better performance and readability.

### API Usage [![Clojars Project](https://img.shields.io/clojars/v/cirru/sepal.svg)](https://clojars.org/cirru/sepal)

```edn
[cirru/sepal "0.2.0"]
```

```clojure
(cirru-sepal.core/write-code [["println" ["+" "2" "2"]]])

(cirru-sepal.analyze/write-file {:ns ["ns" "a.b"], :proc [], :defs {:main! ["defn" "main!" ["a" "b"]]}})
```

Supported forms:

* `[]`
* `{}`
* `#{}`
* `;` and `;;`
* `case`
* `def`
* `defn`
* `defn-`
* `fn`
* `fn*`, `#()`
* `let`
* `loop`
* `ns`
* `doseq`

Read `data/examples/` and `data/compiled/` for details.

### Test

```bash
yarn test
```

### Acknowledgements

* [Quoting Without Confusion](https://blog.8thlight.com/colin-jones/2012/05/22/quoting-without-confusion.html)
  showed the tricky solution for using `` `~'defn `` to remove namespace in expanding

### License

MIT
