(ns cljts.test.relation
  (:refer-clojure :exclude [empty? contains?])
  (:use [midje.sweet])
  (:use [clojure.test])
  (:use [cljts geom relation prep]))


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
   (covers? pp2 p) => true
   (covers? pp p) => false
   (contains? pp2 p) => true
   (contains? pp p) => false
   (overlaps? pp pp2) => true
   (relate? p pp "F**T*****") => false
   (relation pp pp2) => (.toString (.relate pp pp2))))

(facts
 (let [c1 (c 20 30)
       cseq [(c 24 40) (c 34 20) (c 20 12) (c 24 40)]
       cseq2 [(c 0 19) (c 29 38) (c 39 19) (c 0 19)]
       p (point c1)
       prep-p (prepare (point c1))
       prep-l (prepare (line-string [(c 24 30) (c 24 40)]))
       pp (polygon (linear-ring cseq) nil)
       prep-pp (prepare (polygon (linear-ring cseq) nil))
       pp2 (polygon (linear-ring cseq2) nil)
       prep-pp2 (prepare (polygon (linear-ring cseq2) nil))]

   (disjoint? prep-pp pp2) => false
   (intersects? prep-pp pp2) => true
   (touches? prep-pp pp2) => false
   (touches? prep-p pp) => false
   (crosses? prep-l pp) => false
   (within? prep-p pp2) => true
   (within? prep-p pp) => false
   (covers? prep-pp2 p) => true
   (covers? prep-pp p) => false
   (contains? prep-pp2 p) => true
   (contains? prep-pp p) => false
   (overlaps? prep-pp pp2) => true))



