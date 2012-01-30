(ns cljts.analysis
  (:import [com.vividsolutions.jts.geom Geometry]))

(defprotocol SpatialAnalysis
  (distance [this that] "compute distance between two geometries")
  (buffer
    [this distance]
    [this distance quadrant-segment]
    [this distance quadrant-segment end-cap-style]
    "compute buffer area around geometry")
  (convex-hull [this] "computes the smallest convex hull")
  (intersection [this that] "compute intersection geometry")
  (union [this that] "compute union geometry")
  (difference [this that] "compute difference geometry")
  (sym-difference [this that] "compute sym-difference geometry"))

(extend-type Geometry
  SpatialAnalysis
  (distance [this that]
    (.distance this that))
  (buffer
    ([this distance]
       (.buffer this distance))
    ([this distance qs]
       (.buffer this distance qs))
    ([this distance qs ecs]
       (.buffer this distance qs ecs)))
  (convex-hull [this]
    (.convexHull this))
  (intersection [this that]
    (.intersection this that))
  (union [this that]
    (.union this that))
  (difference [this that]
    (.difference this that))
  (sym-difference [this that]
    (.symDifference this that)))


