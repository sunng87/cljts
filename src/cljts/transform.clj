(ns cljts.transform
  "Affine transformations"
  (:use [cljts.geom :only [c]])
  (:import [com.vividsolutions.jts.geom.util
            AffineTransformation AffineTransformationFactory]
           [com.vividsolutions.jts.geom Coordinate Geometry]))

(defn- create-from-control-vectors
  ([c1 c2 c3 c4 c5 c6]
     (AffineTransformationFactory/createFromControlVectors c1 c2 c3 c4 c5 c6))
  ([c1 c2 c3 c4]
     (AffineTransformationFactory/createFromControlVectors c1 c2 c3 c4))
  ([c1 c2]
      (AffineTransformationFactory/createFromControlVectors c1 c2)))

(defn- create-transformation [xys]
  (let [coords (map #(apply c %) (partition 2 xys))]
    (apply create-from-control-vectors coords)))

(defprotocol Transformable
  (transform [this transformation]))

(extend-type Coordinate
  Transformable
  (transform [this transformation]
    (.transform transformation this (Coordinate.))))

(extend-type Geometry
  Transformable
  (transform [this transformation]
    (.transform transformation this)))

(defn- transformation-fn [tr]
  (fn [this]
      (transform this tr)))

(defn transformation [coordinates]
  (transformation-fn (create-transformation coordinates)))

(defn inverse-transformation [coordinates]
  (transformation-fn (.getInverse (create-transformation coordinates))))

(defn identity-transformation []
  (transformation-fn (.setToIdentity (AffineTransformation.))))