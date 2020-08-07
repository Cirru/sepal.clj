
Cirru Sepal for Clojure
----

> Generate Clojure code from vectors(based on [FIPP](https://github.com/brandonbloom/fipp))

```json
[ "defn" "f1" [ "x" ]
  [ "+" "x" "1" ] ]
```

compiles to:

```clojure
(defn f1 [x] (+ x 1))
```

### API Usage [![Clojars Project](https://img.shields.io/clojars/v/cirru/sepal.svg)](https://clojars.org/cirru/sepal)

```edn
[cirru/sepal "0.2.9-a1"]
```

```clojure
(cirru-sepal.core/write-code [["println" ["+" "2" "2"]]])

(cirru-sepal.analyze/write-file {:ns ["ns" "a.b"], :proc [], :defs {:main! ["defn" "main!" ["a" "b"]]}})
```

In file mode, code will be ordered in an order, with definitions sorted by dependencies. `(declare some-function)` would be generated if potential circular dependencies detected.

```text
:ns
:defs
  def1
  def2
  ...
:proc
```

Special forms:

* `[]`
* `{}`
* `#{}`
* `;` and `;;`
* `case`
* `def`
* `defn`, `defn$`(with arity overloading)
* `defn-`, `defn$-`(with arity overloading)
* `fn`, `fn$`(with arity overloading)
* `fn*`, `#()`
* `let`
* `loop`
* `ns`
* `doseq`

Special syntaxes:

* `|str` and `"str` generates "str"
* `#"x` generates `#"x"`

Find working examples in `data/examples/` and `data/compiled/`.

### Test

```bash
yarn test
```

### Acknowledgements

* [Quoting Without Confusion](https://blog.8thlight.com/colin-jones/2012/05/22/quoting-without-confusion.html)
  showed the tricky solution for using `` `~'defn `` to remove namespace in expanding

### License

MIT
