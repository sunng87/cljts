(ns cljts.test.transform
  (:use midje.sweet
        cljts.transform
        [cljts.geom :only [c point]]))

(fact
 (let [rotation 
       (transformation '(0 0    1 0    0 1
                         0 0    0 1   -1 0))]
   (rotation (c 0 0)) => (c 0 0)
   (rotation (c 1 0)) => (c 0 1)
   (rotation (c 0 1)) => (c -1 0)))

(fact
 (let [rotation 
       (transformation '(0 0    1 0    0 1
                         0 0    1 1   -1 1))]
   (rotation (point (c 0 0))) => (point (c 0 0))
   (rotation (point (c 1 0))) => (point (c 1 1))
   (rotation (point (c 0 1))) => (point (c -1 1))))   

(fact
 (let [scale 
       (transformation '(0 0    1 0    0 1
                         0 0    2 1    0 2))]
   (scale (c 0 0)) => (c 0 0)
   (scale (c 1 0)) => (c 2 1)
   (scale (c 0 1)) => (c 0 2)))

(fact
 (let [translate 
       (transformation '(0 0    1 0    0 1
                         5 6    6 6    5 7))]
   (translate (c 0 0)) => (c 5 6)
   (translate (c 1 0)) => (c 6 6)
   (translate (c 0 1)) => (c 5 7)))

(fact
 (let [linear 
       (transformation '(0 0    1 0    0 1
                         0 0    0 0    5 7))]
   (linear (c 0 0)) => (c 0 0)
   (linear (c 1 0)) => (c 0 0)
   (linear (c 0 1)) => (c 5 7)))

(fact
 (let [inverse 
       (inverse-transformation '(0 0    1 0    0 1
                                 0 0    2 1    0 2))]
   (inverse (c 0 0)) => (c 0 0)
   (inverse (c 2 1)) => (c 1 0)
   (inverse (c 0 2)) => (c 0 1)))

(fact
 (let [identity (identity-transformation)
       coord (c (rand) (rand))]
   (identity coord) => coord))
