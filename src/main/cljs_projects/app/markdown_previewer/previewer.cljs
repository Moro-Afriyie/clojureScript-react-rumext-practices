(ns cljs-projects.app.markdown-previewer.previewer
  (:require
   [rumext.v2 :as mf]))


;; Load scripts when needed..
;; (defn load-script [src callback]
;;   (let [script (js/document.createElement "script")]
;;     (set! (.-src script) src)
;;     (set! (.-onload script) callback)
;;     (set! (.-type script) "text/javascript")
;;     (js/document.body.appendChild script)))

;; (defn init-marked []
;;   (load-script "https://cdnjs.cloudflare.com/ajax/libs/marked/4.0.0/marked.min.js"
;;                (fn []
;;                  (js/console.log "Marked script loaded!")
;;       ;; Now you can call the marked API:
;;                  (let [html-content (markdown-to-html "### Hello, Markdown!")]
;;                    (js/console.log html-content)))))

;; had to use this method to bypass the window.marked not working // generally use this approach to import stuff if it doesn't work
(defn markdown-to-html [markdown]
  (if-let [marked-fn (aget js/window "marked")] ;; window["marked"]
    (do
    ;;   (js/console.log "Using Marked.js!")
      ((aget marked-fn "parse") markdown)) ; Call `marked.parse` marked["parse"]
    (do
      (js/setTimeout #(js/console.log "Waiting for Marked.js...") 1000)
      markdown)))



(mf/defc previewer [{:keys [data]}]
  [:div {:class "previewer-container"}
   [:h1 "Previewer"]
;; {:dangerouslySetInnerHTML {:__html "<b>I am bold</b>"}} wasn't working so had to use ref approach
   [:div {:class "previewer-content"
          :ref (fn [el]
                 (when el
                   (set! (.-innerHTML el) (markdown-to-html data))))}]])