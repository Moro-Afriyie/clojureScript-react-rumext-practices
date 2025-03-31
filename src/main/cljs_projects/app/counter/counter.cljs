(ns cljs-projects.app.counter.counter
  (:require
   [rumext.v2 :as mf]))

(mf/defc counter []
  (let [[counter set-counter] (mf/useState 0)
        handle-increment (fn [] (set-counter inc))
        handle-decrement (fn []
                           (set-counter (fn [c] (if (<= c 0) 0 (dec c)))))]
    [:div
     [:h1 "Counter App"]
     [:button {:on-click handle-increment} "Increment"]
     [:button {:on-click handle-decrement} "decrement"]
     [:p (str "Counter: " counter)]]))
