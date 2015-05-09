(ns finstats.core
  (:require [finstats.yahoo :as y]
            [incanter.charts :as charts]
            [incanter.core :as ic]))

(defn- create-dataset
  "creates an incanter dataset from ohlc data"
  [rows]
  {:pre [(every? map? rows)]}
  (let [ks (keys (first rows))]
    (ic/dataset ks
                (map (apply juxt ks) rows))))

;; obtain raw data - do't execute this too often because yahoo is actually giving us this data for free and we shouldn't abuse that
;; (def raw-data (slurp (y/create-http-request "SIE.DE" [2012 1 1] [2013 1 1])))

;; parse the raw-data
;; (def ohlcs (y/parse-string raw-data))
;; (first ohlcs)

;; should return:
;; {:adjusted-close 72.5961, :volume 2979000, :close 77.4255, :low 77.1348, :high 78.5883, :open 78.4042, :date "2013-02-01"}
;; Note that for some reason it returns February 1 2013 instead of January one

;; however, we still got a year of data because (last ohlcs) is February 1 2012 as well

;; For visualizing the data, we first need to reverse the data (because we always look at dates in increasing order)
;; (def ohlcs (vec (rseq ohlcs)))

;; visualize bar-chart with
;; (ic/view (charts/bar-chart (map :date ohlcs) (map :close ohlcs)))

;; visualize candle-stick plot with
;; (ic/view (charts/candle-stick-plot :data (create-dataset ohlcs)))

