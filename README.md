
Cirru Sepal for Clojure
----

> Generates Clojure code from EDN, based on [FIPP](https://github.com/brandonbloom/fipp). This is the essential library for bridging Cirru to Clojure.

### Usages [![Clojars Project](https://img.shields.io/clojars/v/cirru/sepal.svg)](https://clojars.org/cirru/sepal)

```edn
[cirru/sepal "0.2.9"]
```

```clojure
; generates an expression
(cirru-sepal.core/make-string ["println" ["+" "2" "2"]]) ; => string

; generates scripts, i.e. containing multiple expressions
(cirru-sepal.core/write-code [["println" ["+" "2" "2"]]]) ; => string
```

For example:

```json
[ "defn" "f1" [ "x" ]
  [ "+" "x" "1" ] ]
```

runs through `make-string` and it generates:

```clojure
(defn f1 [x] (+ x 1))
```

### Supposed syntax

Special forms:

* `[]`
* `{}`
* `#{}`
* `;` and `;;`(for `comment`)
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

Special syntax:

* `"str` and `|str` generates `"str"`
* `#"x` generates `#"x"`


For example, maps,

```clojure
(def demo {:a 1, :b [2], :c {:d 4}})

(def demo2 {:a 1, :b [2], :c {:d 4}})

(def demo-3 {(f) (f), (g) (g)})

(get demo :a)

(:a demo)
```

can be generated with:

```edn
[
  ["def" "demo"
    ["{}" [":a" "1"] [":b" ["[]" "2"]] [":c" ["{}" [":d" "4"]]]]]

  ["def" "demo2" ["{}" ":a" "1" ":b" ["[]" "2"] ":c" ["{}" ":d" "4"]]]

  ["def" "demo-3" ["{}" [["f"] ["f"]] [["g"] ["g"]]]]

  ["get" "demo" ":a"]

  [":a" "demo"]
]
```

and for structures with pairs in syntax,

```clojure
(cond (< a 1) "little" (> a 1) "great" :else "so-so")

(let [a 1, b (+ 1 1)] (+ a b))

(loop [a 0, b 1] (+ a b))

(doseq [x xs, y ys] (println x y))
```

can be generated with:

```edn
[
  ["cond"
    [["<" "a" "1"] "|little"]
    [[">" "a" "1"] "|great"]
    [":else" "|so-so"]]

  ["let"
    [["a" "1"] ["b" ["+" "1" "1"]]]
    ["+" "a" "b"]]

  ["loop"
    [["a" "0"] ["b" "1"]]
    ["+" "a" "b"]]

  ["doseq"
    [["x" "xs"] ["y" "ys"]]
    ["println" "x" "y"]]
]
```

Find more examples in [`data/`](data/).

### File Mode

`write-file` generates a whole file with namespaces and definitions, also bare scripts.

```clojure
(cirru-sepal.analyze/write-file {:ns ["ns" "a.b"],
                                 :proc [],
                                 :defs {:main! ["defn" "main!" ["a" "b"]]}})
```

Notice this structure, `:defs` holds all `defn` `def` `defonce` forms. There might be dependencies among each function, this library tries detect dependenies of function and sort functions in a right order. `(declare some-function)` will be inserted when potential circular dependencies is detected.

### Test

```bash
yarn test
```

### Acknowledgements

* [Quoting Without Confusion](https://blog.8thlight.com/colin-jones/2012/05/22/quoting-without-confusion.html)
  showed the tricky solution for using `` `~'defn `` to remove namespace in expanding

### License

MIT
