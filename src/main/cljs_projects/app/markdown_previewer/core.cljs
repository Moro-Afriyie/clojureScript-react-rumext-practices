(ns cljs-projects.app.markdown-previewer.core
  (:require
   [rumext.v2 :as mf]
   [cljs-projects.app.markdown-previewer.editor :as app-editor]
   [cljs-projects.app.markdown-previewer.previewer :as app-previewer]))


(mf/defc markdown-previewer []
  (let [[content set-content] (mf/useState "# Welcome to my React Markdown Previewer!

## This is a sub-heading...
### And here's some other cool stuff:

Heres some code, `<div></div>`, between 2 backticks.

```
// this is multi-line code:

function anotherExample(firstLine, lastLine) {
  if (firstLine == '```' && lastLine == '```') {
    return multiLineCode;
  }
}
```

You can also make text **bold**... whoa!
Or _italic_.
Or... wait for it... **_both!_**
And feel free to go crazy ~~crossing stuff out~~.

There's also [links](https://www.freecodecamp.org), and
> Block Quotes!

And if you want to get really crazy, even tables:

Wild Header | Crazy Header | Another Header?
------------ | ------------- | -------------
Your content can | be here, and it | can be here....
And here. | Okay. | I think we get it.

- And of course there are lists.
  - Some are bulleted.
     - With different indentation levels.
        - That look like this.


1. And there are numbered lists too.
1. Use just 1s if you want!
1. And last but not least, let's not forget embedded images:
")

        handle-on-change (fn [event] (let [value  (.. event -target -value)]
                                       (set-content value)))]
    [:div {:class "markdown-container"}
     [:h1 {:class "markdown-heading"} "Markdown Previewer"]
     [:div {:class "markdown-content"}
      (mf/element app-editor/editor #js {:data content :on-change handle-on-change})
      (mf/element app-previewer/previewer #js {:data content})]]))
  
  