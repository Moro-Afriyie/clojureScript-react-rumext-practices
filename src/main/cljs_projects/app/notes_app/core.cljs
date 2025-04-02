(ns  cljs-projects.app.notes-app.core
  (:require
   [rumext.v2 :as mf]
   [cljs-projects.app.notes-app.note :as app-note]
   [cljs-projects.app.notes-app.notes-modal :as app-note-modal]))


(mf/defc notes-app []
  (let [[notes set-notes] (mf/useState
                           ;;Use some-> to safely retrieve the stored data
;;If localStorage.getItem("notes") returns null, it won't pass it to JSON.parse.
;;Convert JSON back into ClojureScript format (js->clj)
                           (or (some-> (js/localStorage.getItem "notes")
                                       js/JSON.parse
                                       (js->clj :keywordize-keys true))
                               []))
        [open-modal set-open-modal] (mf/useState false)
        [selected-note set-selected-note] (mf/useState nil)
        save-to-storage (fn [notes]
                          (js/localStorage.setItem "notes" (js/JSON.stringify (clj->js notes))))

        add-note (fn [{:keys [title description]}]
                   (let [new-item {:title title
                                   :description description
                                   :date (js/Date.now)
                                   :id (js/Date.now)}
                         updated-notes (conj notes new-item)]
                     (set-notes updated-notes)
                     (save-to-storage updated-notes)
                     (set-open-modal false)))

        delete-note (fn [{:keys [id]}]
                      (let [updated-notes (filterv #(not (= (:id %) id)) notes)]
                        (set-notes updated-notes)
                        (save-to-storage updated-notes)))

        update-note (fn [{:keys [id title description]}]
                      (let [updated-notes (mapv (fn [note]
                                                  (if (= (:id note) id)
                                                    (assoc note :title title :description description)
                                                    note))
                                                notes)]
                        (set-notes updated-notes)
                        (save-to-storage updated-notes)))]

    [:div
     [:h1 {:class "text-center text-4xl font-bold" :style {:margin-top "1rem"}} "Notes App"]
     (app-note-modal/notes-modal #js {:note selected-note :set-open-modal set-open-modal :open-modal open-modal :add-note add-note})
     [:div {:class "flex items-center justify-center mt-20"}
      [:div {:class "flex gap-6 flex-wrap bg-gray-200 border border-gray-300 w-[90vw] min-h-50 rounded-2xl p-4"}
       [:div {:class "w-70 min-h-50  flex items-center justify-center border border-yellow-300 text-4xl rounded-xl cursor-pointer bg-yellow-100" :on-click (fn [] ((set-selected-note nil)
                                                                                                                                                                   (set-open-modal true)))}
        [:i {:class "fa-solid fa-plus cursor-pointer" :style {:font-size "5rem"}}]]
       (map (fn [{:keys [id title description date]}]
              (app-note/note #js {:id id :title title :description description :date date :delete-note delete-note}))
            notes)]]]))