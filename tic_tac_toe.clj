(ns tic-tac-toe 
  (:require [clojure.set :as cset]))

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

(defn drawn? [moves] 
  (<= 9 (count moves)))

(defn read-move []
  (->> (read-line)
       (read-string)))

(defn add-move [game move]
  (update-in game [:current-player :moves] conj move))

(defn change-turn [game]
  (let [current-player (:current-player game)
        next-player (:next-player game)]
    (assoc game :current-player next-player
           :next-player current-player)))

(defn valid? [moves move]
  (and (< 0 move 10) (not (moves move))))

(defn get-all-moves [game]
  (let [current-player-moves (:moves (:current-player game))
        next-player-moves (:moves (:next-player game))]
    (cset/union current-player-moves next-player-moves)))

(defn handle-turn [game]
  (let [move (read-move)
        moves (get-all-moves game)]
    (if (valid? moves move)
      (change-turn (add-move game move))
      game)))

(defn conduct-turn [game]
  (let [next-player (:next-player game)
        moves (get-all-moves game)]
    (cond 
      (won? next-player) (assoc game :over true :winner next-player)
      (drawn? moves) (assoc game :over true :drawn true)
      :else (handle-turn game))))

(defn game-not-over? [game]
  (not (:over game)))

(defn results [game]
  (cond
    (:winner game) (str (:name (:winner game)) " ne game khatam kr diya...")
    (:drawn game) (str "Game drawn!!!")
    :else (str "Something went wrong!!!")))

(defn play-game []
  (first (drop-while game-not-over? (iterate conduct-turn game))))

(results (play-game))
