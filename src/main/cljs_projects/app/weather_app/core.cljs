(ns cljs-projects.app.weather-app.core
  (:require [rumext.v2 :as mf]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure.string :as str]
            [goog.functions :as gfn])
  (:require-macros [cljs.core.async.macros :refer [go]]))





(def weather-icons
  {"clear"            "☀️"
   "few clouds"       "🌤️"
   "scattered clouds" "🌤️"
   "broken clouds"    "⛅"
   "clouds"           "☁️"
   "shower rain"      "🌦️"
   "drizzle"          "🌦️"
   "rain"             "🌧️"
   "thunderstorm"     "⛈️"
   "snow"             "❄️"
   "mist"             "🌫️"
   "fog"              "🌫️"
   "haze"             "🌁"
   "smoke"            "🚬"
   "dust"             "🌪️"
   "sand"             "🏜️"
   "tornado"          "🌪️"})

(defn get-weather-icon [condition]
  (let [condition-lc (clojure.string/lower-case (or condition ""))]
    (get weather-icons condition-lc "❔")))

(defn fetch-weather [city]
  (let [url (str "https://api.openweathermap.org/data/2.5/weather?q="
                 city "&appid=289bccae420132772d6aecf6bfe45635&units=metric")
        result-chan (cljs.core.async/chan)]
    (go
      (let [response (<! (http/get url {:with-credentials? false}))]
        (js/console.log "Weather response:" (clj->js response))
        (cljs.core.async/>! result-chan response))) ; send data into the channel
    result-chan)) ; return the channel


(defn fetch-forecast [city]
  (let [url (str "https://api.openweathermap.org/data/2.5/forecast?q="
                 city "&appid=289bccae420132772d6aecf6bfe45635&units=metric")
        result-chan (cljs.core.async/chan)]
    (go
      (let [response (<! (http/get url {:with-credentials? false}))]
        ;; (js/console.log "Weather forecast response:" (clj->js response))
        (cljs.core.async/>! result-chan response)))
    result-chan))

;; (defn fetch-forecast [city]
;;   (let [url (str "https://api.openweathermap.org/data/2.5/forecast?q="
;;                  city "&appid=289bccae420132772d6aecf6bfe45635&units=metric")
;;         result-chan (chan)]
;;     (go
;;       (let [response (<! (http/get url {:with-credentials? false}))]
;;         (when (= 200 (:status response))
;;           (>! result-chan (-> response :body :list (take 8))))) ; next 24 hours (8 x 3h)
;;       result-chan)))


(defn get-today []
  (let [days ["Sunday" "Monday" "Tuesday" "Wednesday" "Thursday" "Friday" "Saturday"]
        today (js/Date.)
        day-index (.getDay today)]
    (get days day-index)))


(defn get-day-name [date-str]
  (let [days ["Sunday" "Monday" "Tuesday" "Wednesday" "Thursday" "Friday" "Saturday"]
        date (js/Date. date-str)]
    (get days (.getDay date))))



(defonce debounced-fetch!
  (gfn/debounce
   (fn [city set-weather-data! set-forecast-data! set-loading!]
     (go
       (set-loading! true)

         ;; Fetch weather
       (let [weather-res (<! (fetch-weather city))
             {:keys [status body]} weather-res]
         (if (= status 200)
           (set-weather-data! body)
           (js/console.log "Failed to fetch weather data. Please try again...")))

         ;; Fetch forecast
       (let [forecast-res (<! (fetch-forecast city))
             {:keys [status body]} forecast-res
             {:keys [list]} body]
         (if (= status 200)
           (set-forecast-data! (take 8 list))
           (js/console.log "Failed to fetch forecast data. Please try again...")))

         ;; ✅ Set loading false *after* all async calls
       (set-loading! false)))
   500)) ;; debounce time in ms




(mf/defc weather-app []
  (let
   [[city set-city] (mf/useState "london")
    [weather-data set-weather-data]  (mf/useState nil)
    [loading set-loading] (mf/useState false)
    [forecast-data set-forecast-data] (mf/useState nil)]
    (mf/with-effect [city]
      (debounced-fetch! city set-weather-data set-forecast-data set-loading))

    (if loading
      [:div {:class
             "bg-gradient-to-b from-[#005a9c] to-[#f59e42] flex items-center justify-center min-h-screen flex items-center justify-center text-white font-sans text-xl"} [:p "loading data...."]]
      (let [{:keys [main weather name wind]} weather-data
            weather-cond1 (-> weather first)]
        (js/console.log "main: " (clj->js main))
        (js/console.log "weather: " (clj->js (:main weather-cond1)))
        (js/console.log "name: " name)
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
              "bg-white/10 backdrop-blur-sm text-white placeholder-white text-sm px-4 py-1.5 rounded-full outline-none w-40"
              :value city
              :on-change (fn [event] (let [value (.. event -target -value)]
                                       (set-city value)))}]]
           (comment "Location & Temp Info")
           [:div
            {:class "mb-10"}
            [:h1 {:class "text-3xl font-bold capitalize"} name]
            [:p {:class "text-sm mb-6"} (get-today)]
            (comment "Temperature & Weather Icon")
            [:div
             {:class "flex mt-6 relative items-center gap-4"}
             [:h2 {:class "text-6xl font-bold"} (js/Math.round (:temp main)) "°"]
             [:div
              {:class "text-9xl absolute left-25 "}
              (get-weather-icon (:main weather-cond1))]]
            [:p {:class "text-lg mt-2"} (:description weather-cond1)]]
           (comment "24 Hour Forecast")
           [:div
            {:class "bg-white/10 backdrop-blur-sm rounded-2xl px-4 py-3 mb-4"}
            [:p {:class "text-sm mb-3"} "24-hour forecast"]
            [:div.flex.justify-between.text-center.text-sm
             (for [{:keys [dt main weather]} forecast-data]
               (let [hour (-> (js/Date. (* dt 1000))  ;; convert UNIX timestamp to JS Date
                              (.toLocaleTimeString "en-US" #js {:hour "numeric" :hour12 true}))
                     temp (js/Math.round (:temp main))
                     weather-cond (-> weather first :main)]
                 ^{:key dt}
                 [:div
                  [:p hour]
                  [:div {:class "text-xl mb-1"} (get-weather-icon weather-cond)]
                  [:p {:class "font-medium"} (str temp "°")]]))]]
           (comment "Extra Stats")
           [:div
            {:class
             "bg-white/10 backdrop-blur-sm rounded-2xl px-6 py-4 grid grid-cols-3 gap-2 text-center text-sm"}
            [:div
             [:p {:class "text-xs text-gray-100"} "Low"]
             [:p {:class "font-semibold text-lg"} (js/Math.round (:temp_min
                                                                  main)) "°C"]]
            [:div
             [:p {:class "text-xs text-gray-100"} "Humidity"]
             [:p {:class "font-semibold text-lg"} (:humidity main) "%"]]
            [:div
             [:p {:class "text-xs text-gray-100"} "Wind"]
             [:p {:class "font-semibold text-lg"} (js/Math.round (:speed
                                                                  wind)) " km/h"]]]]]]))))
 