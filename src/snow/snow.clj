(ns drawing.snow
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/smooth)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :y-param 10})

(defn draw [{flake :flake background :background y-param :y-param}]
  (q/background-image background)
  (def x-params [10 200 390])
  (doseq [x x-params]
    (q/image flake x y-param)))

(defn update [state]
  (if (>= (:y-param state) (q/height))
    (assoc state :y-param 0)
    (update-in state [:y-param] inc)))

(q/defsketch practice
  :title "Snow scene"
  :size [500 500]
  :setup setup
  :settings #(q/pixel-density 1)
  :draw draw
  :update update
  :features [:keep-on-top]
  :middleware [m/fun-mode])
