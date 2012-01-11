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

(unfinished point)

(fact
 (point ...x-y...) => the-point
 (provided
  (point ...x-y...) => the-point))

