(ns cljs-projects.app.notes-app.note
  (:require [rumext.v2 :as mf]))

(defn format-date [date-str]
;;   js/Date. = new Date()  
  ;; (js/Date.now) = Date.now()
  (let [date (js/Date. date-str)]
    (.toLocaleDateString date "en-US" #js {:year "numeric" :month "long" :day "numeric"})))


(mf/defc note [{:keys [id title description date delete-note]}]
  [:div {:class (if id "" "hidden")}
   [:div  {:class "w-70 min-h-50  p-4 flex  flex-col gap-2 border border-yellow-300 text-4xl rounded-xl  bg-yellow-100" :key id}
    [:h6 {:class "text-center text-2xl font-bold capitalize"} title]
    [:div {:class "text-left text-xs flex-grow"}
     [:p {:class ""}  description]]
    [:div {:class "text-xs flex justify-between items-center"}
     [:div {:class ""}
      [:span {:class ""} (format-date date)]]
     [:div {:class "flex gap-6 item-center"}
      [:i {:class "fa-solid fa-pen-to-square cursor-pointer" :style {:font-size "1rem"}}]
      [:i {:class "fa-solid fa-trash cursor-pointer" :style {:font-size "1rem"} :on-click (fn [] (delete-note {:id id}))}]]]]])