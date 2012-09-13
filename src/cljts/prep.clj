(ns cljts.prep
  (:import com.vividsolutions.jts.geom.prep.PreparedGeometryFactory))

(defn prepare [geometry]
  "creates a new prepared geometry"
  (PreparedGeometryFactory/prepare geometry))
