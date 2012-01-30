(ns cljts.test.analysis
  (:refer-clojure :exclude [empty?])
  (:use midje.sweet)
  (:use clojure.test)
  (:use [cljts geom analysis]))

(def p (point (c 20 40)))
(def pp (polygon (linear-ring [(c 30 23)
                               (c 38 23)
                               (c 30 80)
                               (c 30 23)]) []))
(def pp2 (polygon (linear-ring [(c 36 13)
                                (c 34 13)
                                (c 36 32)
                                (c 36 13)]) []))

(facts
 "spatial analysis"
 (buffer p 20) => (.buffer p 20)
 (buffer p 20 3) => (.buffer p 20 3)
 (distance pp pp2) => (.distance pp2 pp)
 (convex-hull pp) => (.convexHull pp)
 (intersection pp pp2) => (.intersection pp pp2)
 (union pp pp2) => (.union pp pp2)
 (difference pp pp2) => (.difference pp pp2)
 (sym-difference pp pp2) => (.symDifference pp pp2))



