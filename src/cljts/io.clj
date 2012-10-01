(ns cljts.io
  (:use [cljts.geom :only [cached-geom-factory]])
  (:import [java.io
            Reader
            Writer])
  (:import [com.vividsolutions.jts.io
            InputStreamInStream
            OutputStreamOutStream
            ByteOrderValues
            WKBReader
            WKBWriter
            WKTReader
            WKTWriter
            ])
  (:import [org.geotools.geojson.geom
            GeometryJSON])
  )

(defn read-wkt
  "read geometry object from a reader contains well-known text"
  [reader & {:keys [precision-model srid]
             :or {precision-model :floating
                  srid 0}}]
  (let [factory (cached-geom-factory precision-model srid)
        wkt-reader (WKTReader. factory)]
    (io! (.read wkt-reader ^Reader reader))))

(defn read-wkt-str
  "read geometry object from a string of well-known text"
  [wkt & {:keys [precision-model srid]
                           :or {precision-model :floating
                                srid 0}}]
  (let [factory (cached-geom-factory precision-model srid)
        wkt-reader (WKTReader. factory)]
    (.read wkt-reader ^String wkt)))

(defn write-wkt
  "write a geometry object as well-known text to a writer"
  [geo writer & {:keys [dimension formatted]
                 :or {dimension 2 formatted false}}]
  (let [wkt-writer (WKTWriter. dimension)]
    (if formatted
      (io! (.writeFormatted wkt-writer geo writer))
      (io! (.write wkt-writer geo writer)))))

(defn write-wkt-str
  "write a geometry object as well-known text"
  [geo & {:keys [dimension formatted]
          :or {dimension 2 formatted false}}]
  (let [wkt-writer (WKTWriter. dimension)]
    (if formatted
      (.writeFormatted wkt-writer geo)
      (.write wkt-writer geo))))

(defn read-wkb
  "read geometry object from well-known binary stream"
  [stream & {:keys [precision-model srid]
             :or {precision-model :floating
                  srid 0}}]
  (let [factory (cached-geom-factory precision-model srid)
        wkb-reader (WKBReader. factory)]
    (io! (.read wkb-reader
                ^InputStreamInStream (InputStreamInStream. stream)))))

(defn read-wkb-bytes
  "read geometry object from a well-known binary byte array"
  [bytes & {:keys [precision-model srid]
            :or {precision-model :floating
                 srid 0}}]
  (let [factory (cached-geom-factory precision-model srid)
        wkb-reader (WKBReader. factory)]
    (.read wkb-reader ^bytes bytes)))

(defn write-wkb
  "write geometry object into a well-known binary stream"
  [geo stream & {:keys [dimension byte-order include-srid?]
                 :or {dimension 2 byte-order :big-endian
                      include-srid? false}}]
  (let [bo (if (= byte-order :big-endian)
                     ByteOrderValues/BIG_ENDIAN
                     ByteOrderValues/LITTLE_ENDIAN)
        wkb-writer (WKBWriter. dimension bo include-srid?)]
    (io! (.write wkb-writer geo
                 ^OutStream (OutputStreamOutStream. stream)))))

(defn write-wkb-bytes
  "write geometry object into a well-known binary byte array"
  [geo & {:keys [dimension byte-order include-srid?]
          :or {dimension 2 byte-order :big-endian
               include-srid? false}}]
  (let [bo (if (= byte-order :big-endian)
             ByteOrderValues/BIG_ENDIAN
             ByteOrderValues/LITTLE_ENDIAN)
        wkb-writer (WKBWriter. dimension bo include-srid?)]
    (.write wkb-writer geo)))

(defn read-geojson
  "read geometry object from a reader that contains a geojson text"
  [input & {:keys [decimals]}]
  (let [reader (GeometryJSON.)]
    (.read reader input)))

(defn write-geojson
  "write geometry as geojson to output"
  ([geo]
    (let [writer (GeometryJSON.)]
      (.toString writer geo)))
  ([geo output]
    (let [writer (GeometryJSON.)]
      (.write writer geo output))))