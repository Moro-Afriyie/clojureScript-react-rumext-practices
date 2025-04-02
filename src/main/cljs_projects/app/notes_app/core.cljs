(ns  cljs-projects.app.notes-app.core
  (:require
   [rumext.v2 :as mf]
   [cljs-projects.app.notes-app.note :as app-note]))


(mf/defc notes-app []
  (let [[notes set-notes] (mf/useState [{:id 1 :title "Note 1" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis lacus tortor. Morbi maximus scelerisque volutpat. Sed aliquet cursus risus nec semper. Mauris sagittis ut elit eu pharetra. Morbi sit amet est vitae nulla porta pretium id nec enim. Maecenas vel volutpat nunc. Fusce posuere sem vel neque finibus, sit amet mollis ipsum accumsan. Etiam lacinia egestas justo, et vehicula mi fringilla a. Aliquam mollis augue sed eros tincidunt dignissim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris quam sapien, finibus vitae ante sit amet, efficitur viverra nisi. Suspendisse sed egestas augue, non blandit tortor.

" :date "2025-04-01T12:00:00Z"}
                                        {:id 2 :title "Note 2" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis lacus tortor. Morbi maximus scelerisque volutpat. Sed aliquet cursus risus nec semper. Mauris sagittis ut elit eu pharetra. Morbi sit amet est vitae nulla porta pretium id nec enim. Maecenas vel volutpat nunc. Fusce posuere sem vel neque finibus, sit amet mollis ipsum accumsan. Etiam lacinia egestas justo, et vehicula mi fringilla a. Aliquam mollis augue sed eros tincidunt dignissim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris quam sapien, finibus vitae ante sit amet, efficitur viverra nisi. Suspendisse sed egestas augue, non blandit tortor.
                   
                   " :date "2025-04-01T12:00:00Z"}
                                        {:id 3 :title "Note 3" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis lacus tortor. Morbi maximus scelerisque volutpat. Sed aliquet cursus risus nec semper. Mauris sagittis ut elit eu pharetra. Morbi sit amet est vitae nulla porta pretium id nec enim. Maecenas vel volutpat nunc. Fusce posuere sem vel neque finibus, sit amet mollis ipsum accumsan. Etiam lacinia egestas justo, et vehicula mi fringilla a. Aliquam mollis augue sed eros tincidunt dignissim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris quam sapien, finibus vitae ante sit amet, efficitur viverra nisi. Suspendisse sed egestas augue, non blandit tortor.
                                               
                                               " :date "2025-04-01T12:00:00Z"}
                                        {:id 4 :title "Note 4" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis lacus tortor. Morbi maximus scelerisque volutpat. Sed aliquet cursus risus nec semper. Mauris sagittis ut elit eu pharetra. Morbi sit amet est vitae nulla porta pretium id nec enim. Maecenas vel volutpat nunc. Fusce posuere sem vel neque finibus, sit amet mollis ipsum accumsan. Etiam lacinia egestas justo, et vehicula mi fringilla a. Aliquam mollis augue sed eros tincidunt dignissim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris quam sapien, finibus vitae ante sit amet, efficitur viverra nisi. Suspendisse sed egestas augue, non blandit tortor.
                                               
                                               " :date "2025-04-01T12:00:00Z"}
                                        {:id 5 :title "Note 5" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis lacus tortor. Morbi maximus scelerisque volutpat. Sed aliquet cursus risus nec semper. Mauris sagittis ut elit eu pharetra. Morbi sit amet est vitae nulla porta pretium id nec enim. Maecenas vel volutpat nunc. Fusce posuere sem vel neque finibus, sit amet mollis ipsum accumsan. Etiam lacinia egestas justo, et vehicula mi fringilla a. Aliquam mollis augue sed eros tincidunt dignissim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris quam sapien, finibus vitae ante sit amet, efficitur viverra nisi. Suspendisse sed egestas augue, non blandit tortor.
                                               
                                               " :date "2025-04-01T12:00:00Z"}])
        [open-modal set-open-modal] (mf/useState false)
        add-note (fn [] (js/console.log "add notes button clicked..."))]
    [:div
     [:div {:class "flex items-center justify-center mt-20"}
      [:div {:class "flex gap-6 flex-wrap bg-gray-200 border border-gray-300 w-[90vw] min-h-[50vh] rounded-2xl p-4"}
       [:div {:class "w-70 min-h-70  flex items-center justify-center border border-yellow-300 text-4xl rounded-xl cursor-pointer bg-yellow-100" :on-click add-note}
        [:i {:class "fa-solid fa-plus cursor-pointer" :style {:font-size "5rem"}}]]
       (map (fn [{:keys [id title content date]}]
              (app-note/note #js {:id id :title title :content content :date date}))
            notes)]]]))