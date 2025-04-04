(ns cljs-projects.app.weather-app.core
  (:require [rumext.v2 :as mf]))


(mf/defc weather-app []
  [:div
   [:div
    {:class
     "bg-gradient-to-b from-[#005a9c] to-[#f59e42] min-h-screen flex items-center justify-center text-white font-sans"}
    [:div
     {:class "w-full max-w-[400px] px-4 py-6"}
     (comment "Top Row: Time and Search")
     [:div
      {:class "flex justify-between items-center mb-6"}
      [:p {:class "text-sm font-light"} "9:41"]
      [:input
       {:type "text",
        :placeholder "Search city",
        :class
        "bg-white/10 backdrop-blur-sm text-white placeholder-white text-sm px-4 py-1.5 rounded-full outline-none w-40"}]]
     (comment "Location & Temp Info")
     [:div
      {:class "mb-8"}
      [:h1 {:class "text-3xl font-bold"} "London"]
      [:p {:class "text-sm mb-6"} "Tuesday"]
      (comment "Temperature & Weather Icon")
      [:div
       {:class "flex items-center gap-4"}
       [:h2 {:class "text-6xl font-bold"} "23°"]
       [:div
        {:class "w-16 h-16 bg-yellow-300 rounded-full relative"}
        [:div
         {:class
          "w-10 h-10 bg-white rounded-full absolute bottom-0 left-6"}]]]
      [:p {:class "text-lg mt-2"} "Sunny"]]
     (comment "24 Hour Forecast")
     [:div
      {:class "bg-white/10 backdrop-blur-sm rounded-2xl px-4 py-3 mb-4"}
      [:p {:class "text-sm mb-3"} "24-hour forecast"]
      [:div
       {:class "flex justify-between text-center text-sm"}
       [:div
        [:div {:class "text-xl mb-1"} "☀️"]
        [:p "Now"]
        [:p {:class "font-medium"} "23°"]]
       [:div
        [:div {:class "text-xl mb-1"} "☀️"]
        [:p "12 PM"]
        [:p {:class "font-medium"} "24°"]]
       [:div
        [:div {:class "text-xl mb-1"} "☀️"]
        [:p "3 PM"]
        [:p {:class "font-medium"} "25°"]]
       [:div
        [:div {:class "text-xl mb-1"} "☀️"]
        [:p "6 PM"]
        [:p {:class "font-medium"} "24°"]]
       [:div
        [:div {:class "text-xl mb-1"} "⛅"]
        [:p "9 PM"]
        [:p {:class "font-medium"} "21°"]]]]
     (comment "Extra Stats")
     [:div
      {:class
       "bg-white/10 backdrop-blur-sm rounded-2xl px-6 py-4 grid grid-cols-3 gap-2 text-center text-sm"}
      [:div
       [:p {:class "text-xs text-gray-100"} "Low"]
       [:p {:class "font-semibold text-lg"} "21°C"]]
      [:div
       [:p {:class "text-xs text-gray-100"} "Humidity"]
       [:p {:class "font-semibold text-lg"} "55%"]]
      [:div
       [:p {:class "text-xs text-gray-100"} "Wind"]
       [:p {:class "font-semibold text-lg"} "13 km/h"]]]]]])