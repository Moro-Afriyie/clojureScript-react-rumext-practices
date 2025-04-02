(ns cljs-projects.app.notes-app.notes-modal
  (:require [rumext.v2 :as mf]))

(mf/defc notes-modal [{:keys [note set-open-modal open-modal add-note]}]
  (let [[title set-title] (mf/useState (or (:title note) ""))
        [description set-description] (mf/useState (or (:description note) ""))]
    [:div
     {:class (if open-modal "" "hidden")}
     [:div {:class "fixed inset-0 bg-black opacity-70"}]
     [:div
      {:class
       "flex overflow-y-auto  overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"}
      [:div
       {:class "relative p-4 w-full max-w-md max-h-full"}
       [:div
        {:class "relative bg-white rounded-lg shadow-sm "}
        [:div
         {:class
          "flex items-center justify-between p-4 md:p-5 border-b rounded-t  border-gray-200"}
         [:h3
          {:class "text-lg text-center font-semibold text-gray-900"}
          (if (or (nil? note) (empty? note))
            "Add New Note"
            "Edit Note")]
         [:button
          {:type "button"
           :class
           "text-gray-400  cursor-pointer bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center"
           :on-click (fn [] (set-open-modal false))}
          [:svg
           {:class "w-3 h-3"
            :aria-hidden "true"
            :xmlns "http://www.w3.org/2000/svg"
            :fill "none"
            :viewBox "0 0 14 14"}
           [:path
            {:stroke "currentColor"
             :stroke-linecap "round"
             :stroke-linejoin "round"
             :stroke-width "2"
             :d "m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"}]]
          [:span {:class "sr-only"} "Close modal"]]]
        (comment "Modal body")
        [:form
         {:class "p-4 md:p-5"}
         [:div
          {:class "grid gap-4 mb-4 grid-cols-2"}
          [:div
           {:class "col-span-2"}
           [:label
            {:for "title"
             :class
             "block mb-2 text-sm font-medium text-gray-900"}
            "Title"]
           [:input
            {:type "text"
             :name "title"
             :id "title"
             :value title
             :class
             "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
             :required ""
             :on-change (fn [event] (let [value (.. event -target -value)]
                                      (set-title value)))}]]

          [:div
           {:class "col-span-2"}
           [:label
            {:for "description"
             :class
             "block mb-2 text-sm font-medium text-gray-900"}
            "Description"]
           [:textarea
            {:id "description"
             :rows "4"
             :value description
             :class
             "block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
             :placeholder "description here..."
             :on-change (fn [event] (let [value (.. event -target -value)]
                                      (set-description value)))}]]]
         [:button
          {:type "button"
           :on-click (fn [event] ((set-description "")
                                  (set-title "")
                                  (add-note {:title title :description description})))
           :class
           "text-white cursor-pointer w-full inline-flex items-center justify-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"}

          "Save"]]]]]]))