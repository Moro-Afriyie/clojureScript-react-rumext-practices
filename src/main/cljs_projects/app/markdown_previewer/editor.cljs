(ns cljs-projects.app.markdown-previewer.editor
  (:require
   [rumext.v2 :as mf]))

(mf/defc editor [{:keys [data on-change]}]
  [:div {:class "markdown-editor-container"}
   [:h1 "Editor"]
   [:div
    [:textarea {:rows "50" :cols "80" :value data :on-change on-change}]]])