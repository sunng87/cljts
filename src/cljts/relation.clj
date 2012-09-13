(ns cljts.relation
  (:refer-clojure :exclude [contains?])
  (:import [com.vividsolutions.jts.geom Geometry])
  (:import [com.vividsolutions.jts.geom.prep PreparedGeometry]))

(defprotocol GeometryRelations
  (equals? [this that]
    "Given two (topologically closed) geometric objects \"a\" and \"b\":
a.Equals(b) ⇔ a ⊆ b ∧ b ⊆ a")
  (disjoint? [this that]
    "Given two (topologically closed) geometric objects \"a\" and \"b\":
a.Disjoint(b) ⇔ a ∩ b = ∅")
  (intersects? [this that]
    "a.Intersects(b) ⇔ ! a.Disjoint(b)")
  (touches? [this that]
    "The Touches relationship between two geometric objects \"a\" and \"b\" applies to the A/A, L/L, L/A, P/A and P/L groups of relationships but not to the P/P group. It is defined as
a.Touch(b) ⇔ (I(a)∩I(b)=∅)∧(a∩b)≠∅")
  (crosses? [this that]
    "The Crosses relationship applies to P/L, P/A, L/L and L/A situations. It is defined as
a.Cross(b) ⇔ [I(a)∩I(b)≠∅ ∧ (a ∩ b ≠a) ∧ (a ∩ b ≠b)]")
  (within? [this that]
    "The Within relationship is defined as: a.Within(b) ⇔ (a∩b=a) ∧ (I(a)∩E(b)=∅)")
  (contains? [this that]
    "a.Contains(b) ⇔ b.Within(a)")
  (overlaps? [this that]
    "The Overlaps relationship is defined for A/A, L/L and P/P situations. It is defined as
a.Overlaps(b) ⇔ ( dim(I(a)) = dim(I(b)) = dim(I(a) ∩ I(b))) ∧ (a ∩ b ≠ a) ∧ (a ∩ b ≠ b)")
  (relate? [this that relation]
    "check relationship by DE9-IM string")
  (relation [this that]
    "get DE9-IM string between to objects"))

(extend-type Geometry
  GeometryRelations
  (equals? [this that]
    (.equals this that))
  (disjoint? [this that]
    (.disjoint this that))
  (intersects? [this that]
    (.intersects this that))
  (touches? [this that]
    (.touches this that))
  (crosses? [this that]
    (.crosses this that))
  (within? [this that]
    (.within this that))
  (overlaps? [this that]
    (.overlaps this that))
  (relate? [this that de9-im]
    (.relate this that de9-im))
  (relation [this that]
    (.toString (.relate this that))))

(extend-type PreparedGeometry
  GeometryRelations
  (disjoint? [this that]
    (.disjoint this that))
  (intersects? [this that]
    (.intersects this that))
  (touches? [this that]
    (.touches this that))
  (crosses? [this that]
    (.crosses this that))
  (within? [this that]
    (.within this that))
  (overlaps? [this that]
    (.overlaps this that)))
