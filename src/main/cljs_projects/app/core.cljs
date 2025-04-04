;; (ns cljs-projects.app.core
;;   (:require [reagent.dom :as rdom]))

;; (defn app []
;;   [:h1 "Create Reagent App"])

;; (defn render []
;;   (rdom/render [app] (.getElementById js/document "root")))

;; (defn ^:export main []
;;   (render))

;; (defn ^:dev/after-load reload! []
;;   (render))
;; (ns cljs-projects.app.core
;;   (:require [rumext.v2 :as mf]))  ;; Ensure correct import for Rumext

(ns cljs-projects.app.core
  (:require
   [goog.dom :as gdom]
   [rumext.v2 :as mf]
   [cljs-projects.app.counter.counter :as app-counter]
   [cljs-projects.app.markdown-previewer.core :as app-markdown-previewer]
   [cljs-projects.app.notes-app.core :as app-notes]
   [cljs-projects.app.weather-app.core :as app-weather]))


(mf/defc app []
  [:div
  ;;  (mf/element  app-counter/counter)
  ;;  (mf/element app-markdown-previewer/markdown-previewer)
  ;;  (mf/element app-notes/notes-app)
   (mf/element app-weather/weather-app)
   ])  


(defonce root-el (gdom/getElement "root"))

(defn ^:export main []
  (when root-el
    (let [root (mf/create-root root-el)]
      (mf/render! root (mf/element app)))))  


(defn ^:dev/after-load reload! []
  (main))
