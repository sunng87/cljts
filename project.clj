(defproject cljts "0.3.0-SNAPSHOT"
  :description "Clojure wrapper of JTS, implements the Simple Feature Spec of Open Geospatial Consortium (OGC)."
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.vividsolutions/jts "1.13"]
                 [org.geotools/gt-geojson "8.2"]]
  :repositories [["opengeo" "http://repo.opengeo.org/"]]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]]
                   :dependencies [[midje "1.6.3"]]}})
