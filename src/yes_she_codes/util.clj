(ns yes-she-codes.util
  (:require [java-time :as time]))

(defn str-to-long [valor]
  (Long/parseLong (clojure.string/replace valor #" " "")))

; outra forma de pegar a data em string sem usar o substr
(defn retorna-mes [data]
  (second (re-matches #"\d{4}-(\d{2})-\d{2}" data)))

; função intermediária genérica (assim como a insere-entidade)
; para usar nos filtros de compra
(defn filtra-compras [funcao compras]
  (vec (filter funcao compras)))

(defprotocol ExtratorDeMes
  (mes-da-data [data]))

(extend-type java.lang.String ExtratorDeMes
  (mes-da-data [data]
    (->> data
         (re-matches #"\d{4}-(\d{2})-\d{2}")
         second
         Long/valueOf)))

(extend-type java.time.LocalDate ExtratorDeMes
  (mes-da-data [data]
    (.getValue (time/month data))))

(defn data-valida? [data]
  (re-matches #"\d{4}-(\d{2})-\d{2}" data))

(defn proximo-id [entidades]
  (if-not (empty? entidades)
    (+ 1 (apply max (map :id entidades)))
    1))