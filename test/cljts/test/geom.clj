(ns cljts.test.geom
  (:use [clojure.test])
  (:use [cljts.geom])
  (:use [midje.sweet])
  (:import [com.vividsolutions.jts.geom
            Coordinate
            GeometryFactory
            PrecisionModel]))

(def geom-factory (GeometryFactory.))
(def the-point (.createPoint geom-factory
                             (Coordinate. 20 30)))

(fact
 (c 50 23) => (Coordinate. 50 23)
 (c 118 43 50.3) => (Coordinate. 118 43 50.3))

;(unfinished point)

(fact
 (point (c 20 30)) => the-point)

;(unfinished linestring)

(def cseq [(c 20 30) (c 30 40) (c 40 50)])

(fact
 (line-string cseq) =>
 (.createLineString geom-factory (into-array Coordinate cseq)))

(def cseq-ring [(c 20 30) (c 30 40) (c 40 50) (c 20 30)])
(fact
 (linear-ring cseq-ring) =>
 (.createLinearRing geom-factory (into-array Coordinate cseq-ring)))

