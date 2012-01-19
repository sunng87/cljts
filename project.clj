(defproject cljts "0.1.0-SNAPSHOT"
  :description "Clojure wrapper of JTS, implements the Simple Feature Spec of Open Geospatial Consortium (OGC)."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [com.vividsolutions/jts "1.12"]]
  :dev-dependencies [[lein-midje "1.0.7"]
                     [codox "0.3.4"]
                     [midje "1.3.1"]]
  :warn-on-reflection false)
