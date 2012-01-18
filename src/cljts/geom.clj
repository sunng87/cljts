(ns cljts.geom
  (:import [com.vividsolutions.jts.geom
            GeometryFactory
            PrecisionModel
            Coordinate
            LinearRing
            Point
            Polygon]))

(defn c
  "create a coordinate object."
  ([x y] (Coordinate. x y))
  ([x y z] (Coordinate. x y z)))

(def ^{:private true} precisions
  {:floating PrecisionModel/FLOATING
   :floating-single PrecisionModel/FLOATING_SINGLE
   :fixed PrecisionModel/FIXED})

(def ^{:private true} geom-factory-cache
  (atom {(str 0 :floating) (GeometryFactory.)}))

(defn- cached-geom-factory [pm srid]
  (let [key (str pm srid)]
    (when-not (contains? @geom-factory-cache key)
      (swap! geom-factory-cache assoc key
             (GeometryFactory. (PrecisionModel. (precisions pm))
                               srid)))
    (@geom-factory-cache key)))

(defmacro defgeom [geom-type doc args bodyfn]
  `(defn ~geom-type ~doc [~@args & {:keys [precision-model# srid#]
                        :or {precision-model# :floating
                             srid# 0}}]
     (let [geom-factory# (cached-geom-factory precision-model# srid#)]
       (~bodyfn geom-factory# ~@args))))

(defgeom point
  "create a jts point object"
  [coordinate]
  (fn [factory coordinate]
    (.createPoint factory coordinate)))

(defgeom line-string
  "create a jts linestring"
  [coordinates]
  (fn [factory coordinates]
    (.createLineString factory (into-array Coordinate coordinates))))

(defgeom linear-ring
  "create a jts linear ring, which is useful to create polygons"
  [cs]
  (fn [factory coordinates]
    (.createLinearRing factory (into-array Coordinate coordinates))))

(defgeom polygon
  "create a jts polygon"
  [ring rings]
  (fn [factory ring rings]
    (.createPolygon factory ring (into-array LinearRing rings))))

(defgeom multi-point
  "create a multi-point with some points"
  [ps]
  (fn [factory points]
    (.createMultiPoint factory (into-array Point points))))

(defgeom multi-polygon
  "create a multi-polygon with some polygons"
  [ps]
  (fn [factory polygons]
    (.createMultiPolygon factory (into-array Polygon polygons))))


