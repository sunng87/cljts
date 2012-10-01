(defproject cljts "0.2.0"
  :description "Clojure wrapper of JTS, implements the Simple Feature Spec of Open Geospatial Consortium (OGC)."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.vividsolutions/jts "1.12"]
                 [midje "1.4.0"]]
  :profiles {:dev {:plugins [[lein-midje "2.0.0-SNAPSHOT"]]}}
  :warn-on-reflection true)
