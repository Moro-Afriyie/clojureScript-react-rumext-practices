(ns  cljs-projects.app.notes-app.core
  (:require
   [rumext.v2 :as mf]))


(mf/defc notes-app []
  [:div
   [:h1 "Welcome to my notes App"]])