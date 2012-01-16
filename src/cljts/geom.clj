(ns cljts.geom
  (:import [com.vividsolutions.jts.geom
            GeometryFactory
            PrecisionModel
            Coordinate]))

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

(defmacro defgeom [geom-type bodyfn]
  `(defn ~geom-type [args#
                     & {:keys [precision-model# srid#]
                        :or {precision-model# :floating
                             srid# 0}}]
     (let [geom-factory# (cached-geom-factory precision-model# srid#)]
       (~bodyfn geom-factory# args#))))

(defgeom point
  (fn [factory coordinate]
    (.createPoint factory coordinate)))

(defgeom linestring
  (fn [factory coordinates]
    (.createLineString factory (into-array Coordinate coordinates))))

(defgeom linearring
  (fn [factory coordinates]
    (.createLinearRing factory (into-array Coordinate coordinates))))

