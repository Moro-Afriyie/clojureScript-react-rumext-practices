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

;; main application

(ns cljs-projects.app.core
  (:require
   [goog.dom :as gdom]
   [rumext.v2 :as mf]))


(mf/defc title [{:keys [name]}]
  [:div {:class "label"} name])


(mf/defc app []
  [:div
   [:h1 "My Rumext App"]
   (mf/element title #js {:name "title component"})])  


(defonce root-el (gdom/getElement "root"))

(defn ^:export main []
  (when root-el
    (let [root (mf/create-root root-el)]
      (mf/render! root (mf/element app)))))  


(defn ^:dev/after-load reload! []
  (main))
