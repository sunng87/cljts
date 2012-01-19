(ns cljts.geom
  (:refer-clojure :exclude [empty?])
  (:import [com.vividsolutions.jts.geom
            GeometryFactory
            PrecisionModel
            PrecisionModel$Type
            Coordinate
            LinearRing
            Point
            Polygon
            Geometry]))

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
             (GeometryFactory. (PrecisionModel. ^PrecisionModel$Type (precisions pm))
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
    (.createPoint ^GeometryFactory factory
                  ^Coordinate coordinate)))

(defgeom line-string
  "create a jts linestring"
  [coordinates]
  (fn [factory coordinates]
    (.createLineString ^GeometryFactory factory
                       #^"[Lcom.vividsolutions.jts.geom.Coordinate;"
                       (into-array Coordinate coordinates))))

(defgeom linear-ring
  "create a jts linear ring, which is useful to create polygons"
  [cs]
  (fn [factory coordinates]
    (.createLinearRing ^GeometryFactory factory
                       #^"[Lcom.vividsolutions.jts.geom.Coordinate;"
                       (into-array Coordinate coordinates))))

(defgeom polygon
  "create a jts polygon"
  [ring rings]
  (fn [factory ring rings]
    (.createPolygon ^GeometryFactory factory
                    ^LinearRing ring
                   (into-array LinearRing rings))))

(defgeom multi-point
  "create a multi-point with some points"
  [ps]
  (fn [factory points]
    (.createMultiPoint ^GeometryFactory factory
                       #^"[Lcom.vividsolutions.jts.geom.Point;"
                       (into-array Point points))))

(defgeom multi-polygon
  "create a multi-polygon with some polygons"
  [ps]
  (fn [factory polygons]
    (.createMultiPolygon ^GeometryFactory factory
                         (into-array Polygon polygons))))

(defprotocol GeometryProperties
  (area [this] "return the area of geometry object, 0 for D0 and D1 objects")
  (boundary [this] "return the boundary of geometry object, which is also a geometry object")
  (centroid [this] "return the centroid point")
  (coordinates [this] "return a vector contains all coordinates of the object")
  (dimension [this] "return the dimension value of the object")
  (envelope [this] "return the boundary box(bbox) of the object")
  (interior-point [this] "return the interior point")
  (length [this] "return the length of the object, 0 for D0 objects")
  (srid [this] "return the srid")
  (n-geometries [this] "return the number of geometries in the object")
  (n-points [this] "return the number of points in the objects")
  (empty? [this] "test if the geometry is an empty one")
  (simple? [this]
    "test if the geometry is a simple one. You can find the definition to simple at: http://tsusiatsoftware.net/jts/javadoc/com/vividsolutions/jts/geom/Geometry.html#isSimple()"))

(extend-type Geometry
  GeometryProperties
  (area [this]
    (.getArea this))
  (boundary [this]
    (.getBoundary this))
  (centroid [this]
    (.getCentroid this))
  (coordinates [this]
    (into [] (.getCoordinates this)))
  (dimension [this]
    (.getDimension this))
  (envelope [this]
    (.getEnvelope this))
  (interior-point [this]
    (.getInteriorPoint this))
  (length [this]
    (.getLength this))
  (srid [this]
    (.getSRID this))
  (n-geometries [this]
    (.getNumGeometries this))
  (n-points [this]
    (.getNumPoints this))
  (empty? [this]
    (.isEmpty this))
  (simple? [this]
    (.isSimple this)))

