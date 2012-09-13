# cljts

A clojure library wraps JTS, implements Simple Feature
Specification(OGC).
cljts provides a set of clojure API to manipulate geometry objects,
includes:

* Create SFS qualified geometry objects: Point, LineString, Polygon
  and more
* Spatial relationship (DE9-IM)
* Spatial analysis
* IO, Serialization (WKB & WKT)

## Usage

### Leiningen 

    [cljts "0.1.0"]

### API

Check [API documents](http://sunng87.github.com/cljts/).

## Example

### Geometry

Creating common JTS geometry objects:

```clojure
(use 'cljts.geom)

;; Point
(point (c 20 30))

;; LineString
(line-string [(c 20 30) (c 20 40)])

;; LinearRing
(linear-ring [(c 20 40) (c 20 46) (c 34 56) (c 20 40)])

;; Polygon without holes
(polygon (linear-ring [(c 20 40) (c 20 46) (c 34 56) (c 20 40)]) nil)

;; Polygon with a hole
(polygon 
  (linear-ring [(c 20 30) (c 30 40) (c 40 55) (c 20 30)])
  [(linear-ring [(c 22 28) (c 28 42) (c 38 50) (c 22 28)])])
```

Test spatial relationship between geometry objects. (Based on DE9-IM)

```clojure
(use 'cljts.relation)

(touches? l1 l2)
(within? p p2)
;; ...
;; get DE9-IM string
(relation p1 p2)

```

Test spatial relationship using prepared geometries. Please see
[this blog post](http://lin-ear-th-inking.blogspot.com/2007/08/preparedgeometry-efficient-batch.html)
for details on prepared geometries. Please note that prepared geometry
should be the first argument for a relationship function.

```clojure
(use 'cljts.prep)
(def prepared-polygon (prepare (polygon (linear-ring [(c 20 40) (c 20 46) (c 34 56) (c 20 40)]) nil)))
(contains? prepared-polygon point)

```

Basic spatial analysis functions:

```clojure
(use 'cljts.analysis)
(buffer p 20)
;; ...
```

Serialize geometry objects to Well-Known Text and Well-Known Binary format:

```clojure
(use 'cljts.io)
(write-wkt g)
(write-wkb-bytes data)
(read-wkt some-reader)
```

Affine transformations:

```clojure
(use 'cljts.transform)
(def rotate-clockwise
           (transformation [(c 0 0) (c 1 0) (c 0 1)
                            (c 0 0) (c 0 1) (c -1 0)]))
(def rotate-counterclockwise
           (inverse-transformation [(c 0 0) (c 1 0) (c 0 1)
                                    (c 0 0) (c 0 1) (c -1 0)]))
(rotate-clockwise (line-string [(c 0 0) (c 2 2)]))
;;; user> #<LineString LINESTRING (0 0, -2 2)>
(rotate-counterclockwise (line-string [(c 0 0) (c 2 2)]))
;;; user> #<LineString LINESTRING (0 0, 2 -2)>

```

## License

Copyright (C) 2012 Sun Ning

Distributed under the Eclipse Public License, the same as Clojure.
