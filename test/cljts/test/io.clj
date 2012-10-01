(ns cljts.test.io
  (:refer-clojure :exclude [empty?])
  (:use clojure.test)
  (:use midje.sweet)
  (:use [cljts io geom])
  (:import [java.io StringWriter StringReader
            ByteArrayInputStream ByteArrayOutputStream])
  (:import [com.vividsolutions.jts.io WKBWriter]))

(fact
 (write-wkt-str (line-string [(c 50 20) (c 40 3)]))
 => "LINESTRING (50 20, 40 3)")

(fact
 (read-wkt-str "LINESTRING (50 20, 40 3)")
 => (line-string [(c 50 20) (c 40 3)]))

(fact
 (let [sw (StringWriter.)
       geo (line-string [(c 50 20) (c 40 3)])]
   (write-wkt geo sw)
   (.toString sw) => "LINESTRING (50 20, 40 3)"))

(fact
 (let [sr (StringReader. "LINESTRING (50 20, 40 3)")
       geo (read-wkt sr)]
   geo => (line-string [(c 50 20) (c 40 3)])))

(fact
 (WKBWriter/toHex (write-wkb-bytes (line-string [(c 50 20) (c 40 3)])))
 => "0000000002000000024049000000000000403400000000000040440000000000004008000000000000")

(fact
 (let [g (line-string [(c 50 20) (c 40 3)])]
   (read-wkb-bytes (write-wkb-bytes g)) => g))

(fact
 (let [g (line-string [(c 50 20) (c 40 3)])
       bs (write-wkb-bytes g)
       bis (ByteArrayInputStream. bs)]
   (read-wkb bis) => g))

(fact
 (let [g (line-string [(c 50 20) (c 40 3)])
       bos (ByteArrayOutputStream.)]
   (write-wkb g bos)
   (WKBWriter/toHex (.toByteArray bos)) =>
   "0000000002000000024049000000000000403400000000000040440000000000004008000000000000"))

(fact
  (write-geojson (line-string [(c 50 20) (c 40 3)]))
  => "{\"type\":\"LineString\",\"coordinates\":[[50,20],[40,3]]}")

(fact
  (read-geojson "{\"type\":\"LineString\",\"coordinates\":[[50,20],[40,3]]}")
  => (line-string [(c 50 20) (c 40 3)]))

(fact
  (let [sw (StringWriter.)
        geo (line-string [(c 50 20) (c 40 3)])]
    (write-geojson geo sw)
    (.toString sw) => "{\"type\":\"LineString\",\"coordinates\":[[50,20],[40,3]]}"))

(fact
  (let [sr (StringReader. "{\"type\":\"LineString\",\"coordinates\":[[50,20],[40,3]]}")
        geo (read-geojson sr)]
    geo => (line-string [(c 50 20) (c 40 3)])))
