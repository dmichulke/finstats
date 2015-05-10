(ns finstats.yahoo
  (:require [clojure.string :as str]))

(def ^:private base-url "http://ichart.finance.yahoo.com/table.csv")
(def ^:private default-params "g=d&ignore=.csv")

(defn create-http-request
  "creates the http request to obtain the required symbol for the given period."
  [sym start end]
  {:pre [(string? sym)
         (every? #(every? integer? %) [start end])]}
  (let [params (concat [sym] start end)
        kv-pairs (->> params
                      (map #(str %1 "=" %2) "scbafed")
                      (cons default-params)
                      (str/join "&"))]
    (str base-url "?" kv-pairs)))


(defn parse-string
  [s]
  {:pre [(string? s)]}
  (->> (str/split-lines s) ;; splits by newline
       rest ;; drops the first line (the header)
       (map #(str/split % #",")) ;; splits each line by comma
       (map #(cons (first %) (map read-string (rest %)))) ;; parses each element of the row except the first
       (mapv #(zipmap [:date :open :high :low :close :volume :adjusted-close] %)))
       
  ) 

