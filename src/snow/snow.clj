(ns drawing.snow
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def x-params [10 200 390])

(defn setup []
  (q/smooth)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :y-params [{:y 10 :speed 1} {:y 150 :speed 4} {:y 50 :speed 2}]})

(defn update-y
  [{y :y speed :speed :as m}]
  (if (>= y (q/height))
    (assoc m :y 0)
    (update-in m [:y] + speed)))

(defn update [state]
  (let [y-params (:y-params state)]
    (assoc state :y-params (map update-y y-params))))

(defn draw [{flake :flake background :background y-params :y-params}]
  (q/background-image background)
  (dotimes [n 3]
    (q/image flake (nth x-params n) (:y (nth y-params n)))))

(q/defsketch practice
  :title "Snow scene"
  :size [500 500]
  :setup setup
  :settings #(q/pixel-density 1)
  :draw draw
  :update update
  :features [:keep-on-top]
  :middleware [m/fun-mode])
