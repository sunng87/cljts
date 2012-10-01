(ns cljts.transform
  "Affine transformations"
  (:use [cljts.geom :only [c]])
  (:import [com.vividsolutions.jts.geom.util
            AffineTransformation AffineTransformationFactory]
           [com.vividsolutions.jts.geom Coordinate Geometry]))

(defn- create-from-control-vectors
  ([c1 c2 c3 c4 c5 c6]
     (AffineTransformationFactory/createFromControlVectors
      ^Coordinate c1
      ^Coordinate c2
      ^Coordinate c3
      ^Coordinate c4
      ^Coordinate c5
      ^Coordinate c6))
  ([c1 c2 c3 c4]
     (AffineTransformationFactory/createFromControlVectors
      ^Coordinate c1
      ^Coordinate c2
      ^Coordinate c3
      ^Coordinate c4))
  ([c1 c2]
     (AffineTransformationFactory/createFromControlVectors
      ^Coordinate c1
      ^Coordinate c2)))

(defn- create-transformation [coordinates]
  (apply create-from-control-vectors coordinates))

(defprotocol Transformable
  (transform [this transformation]))

(extend-type Coordinate
  Transformable
  (transform [this transformation]
    (.transform ^AffineTransformation transformation this (Coordinate.))))

(extend-type Geometry
  Transformable
  (transform [this transformation]
    (.transform ^AffineTransformation transformation this)))

(defn- transformation-fn [tr]
  (fn [this]
      (transform this tr)))

(defn transformation
  "Creates a transformation function from 2, 4 or 6 coordinates which specify coordinates before and after transformation"
  [coordinates]
  (transformation-fn (create-transformation coordinates)))

(defn inverse-transformation [coordinates]
  "Creates a transformation function which provides inverse transformation. Please note that not all transformations are inversible, it will throw an exception if no inverse exists"
  (transformation-fn
   (.getInverse ^AffineTransformation (create-transformation coordinates))))

(defn identity-transformation [this]
  "Identity transformation"
  (let [identity-tr (.setToIdentity (AffineTransformation.))]
    ((transformation-fn identity-tr) this)))
