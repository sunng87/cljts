(ns cljts.test.relation
  (:refer-clojure :exclude [empty? contains?])
  (:use [midje.sweet])
  (:use [clojure.test])
  (:use [cljts geom relation]))


(facts
 (let [c1 (c 20 30)
       cseq [(c 24 40) (c 34 20) (c 20 12) (c 24 40)]
       cseq2 [(c 0 19) (c 29 38) (c 39 19) (c 0 19)]
       p (point c1)
       l (line-string [(c 24 30) (c 24 40)])
       pp (polygon (linear-ring cseq) nil)
       pp2 (polygon (linear-ring cseq2) nil)]

   (equals? pp pp2) => false
   (disjoint? pp pp2) => false
   (intersects? pp pp2) => true
   (touches? pp pp2) => false
   (touches? p pp) => false
   (crosses? l pp) => false
   (within? p pp2) => true
   (within? p pp) => false
   (overlaps? pp pp2) => true
   (relate? p pp "F**T*****") => false
   (relation pp pp2) => (.toString (.relate pp pp2))))



