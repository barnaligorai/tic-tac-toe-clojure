(ns tic-tac-toe)

(def player1 {:name "player1" 
              :symbol :X 
              :moves #{}})

(def player2 {:name "player2" 
              :symbol :O 
              :moves #{}})

(def game {:current-player player1
           :next-player player2})



(def winning-moves #{#{1 2 3} #{4 5 6} #{7 8 9} #{1 4 7} #{2 5 8} #{3 6 9} #{1 5 9} #{3 5 7}})

(defn winner? [player]
  (some #(every? (player :moves) %) winning-moves))

(defn move [] 
    (->> (read-line)
         (read-string)))

(defn add-move [game move]
    (update-in game [:current-player :moves] conj move))

(defn change-turn [game]
  (let [current-player (game :current-player) 
        next-player (game :next-player)]
    (assoc game :current-player next-player
            :next-player current-player)))

(loop [{:keys [next-player] :as game} game] 
  (if (winner? next-player)
    (println (next-player :name) "ne game khatam kr diya...")
    (recur (change-turn (add-move game (move))))))
