(ns drawing.snow
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/smooth)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :params [{:x 10  :swing 1 :y 10  :speed 1}
            {:x 200 :swing 3 :y 100 :speed 4}
            {:x 390 :swing 2 :y 50  :speed 2}]})

(defn update-y
  [{y :y speed :speed :as m}]
  (if (>= y (q/height))
    (assoc m :y 0)
    (update-in m [:y] + speed)))

(defn update-x
  [{x :x swing :swing y :y :as m}]
  (cond
    (< x 0) (assoc m :x (q/width))                                  ;; too left
    (< x (q/width)) (update-in m [:x] + (* swing (q/sin (/ y 50)))) ;; within frame
    :else (assoc m :x 0)))                                        ;; too right

(defn update [state]
  (let [params  (:params state)
        params (map update-y params)
        params (map update-x params)]
    (assoc state :params params)))

(defn draw [{flake :flake background :background params :params}]
  (q/background-image background)
  (dotimes [n 3]
    (let [param (nth params n)]
        (q/image flake (:x param) (:y param)))))

(q/defsketch practice
  :title "Snow scene"
  :size [500 500]
  :setup setup
  :settings #(q/pixel-density 1)
  :draw draw
  :update update
  :features [:keep-on-top]
  :middleware [m/fun-mode])
