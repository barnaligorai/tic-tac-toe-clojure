(ns tic-tac-toe 
  (:require [clojure.set :as clojure.set]))

(def player1 {:name "player1"
              :symbol :X
              :moves #{}})

(def player2 {:name "player2"
              :symbol :O
              :moves #{}})

(def game {:current-player player1
           :next-player player2})

(def winning-moves #{#{1 2 3} #{4 5 6} #{7 8 9} #{1 4 7} #{2 5 8} #{3 6 9} #{1 5 9} #{3 5 7}})

(defn won? [player]
  (some #(every? (:moves player) %) winning-moves))

(defn drawn? [moves1 moves2] 
  (<= 9 (count (clojure.set/union moves1 moves2))))

(defn move []
  (->> (read-line)
       (read-string)))

(defn add-move [game move]
  (update-in game [:current-player :moves] conj move))

(defn change-turn [game]
  (let [current-player (:current-player game)
        next-player (:next-player game)]
    (assoc game :current-player next-player
           :next-player current-player)))

(defn conduct-turn [game]
  (let [current-player (:current-player game)
        next-player (:next-player game)
        current-player-moves (:moves current-player)
        next-player-moves (:moves next-player)]
    (cond 
      (won? next-player) (assoc game :over true :winner next-player)
      (drawn? current-player-moves next-player-moves) (assoc game :over true :drawn true)
      :else (change-turn (add-move game (move))))))

(defn game-not-over? [game]
  (not (:over game)))

(defn results [game]
  (cond
    (:winner game) (str (:name (:winner game)) " ne game khatam kr diya...")
    (:drawn game) (str "Game drawn!!!"))
  )

(defn play-game []
  (first (drop-while game-not-over? (iterate conduct-turn game))))

(results (play-game))
