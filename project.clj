(defproject me.ro6/repro-post-message-fail "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojurescript "1.10.520"]
                 [org.clojure/core.async "0.4.490"]
                 [binaryage/chromex "0.7.2"]
                 [hoplon "7.2.0"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.18"]]

  :source-paths ["src/background"
                 "src/popup"
                 "src/content_script"
                 "src/common"]

  :clean-targets ^{:protect false} ["target"
                                    "resources/unpacked/compiled"
                                    "resources/release/compiled"]

  :cljsbuild {:builds {}} ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413)

  :profiles {:unpacked
             {:dependencies [[org.clojure/clojure "1.10.0"]
                             [binaryage/devtools "0.9.10"]
                             [figwheel "0.5.18"]
                             [figwheel-sidecar "0.5.18"]]
              :cljsbuild    {:builds
                             {:background
                              {:source-paths ["src/background" "src/common"]
                               :figwheel     true
                               :compiler     {:output-to     "resources/unpacked/compiled/background/main.js"
                                              :output-dir    "resources/unpacked/compiled/background"
                                              :asset-path    "compiled/background"
                                              :preloads      [devtools.preload figwheel.preload]
                                              :main          repro.plugin-background.main
                                              :optimizations :none
                                              :source-map    true}}

                              :popup
                              {:source-paths ["src/popup" "src/common"]
                               :figwheel     true
                               :compiler     {:output-to     "resources/unpacked/compiled/popup/main.js"
                                              :output-dir    "resources/unpacked/compiled/popup"
                                              :asset-path    "compiled/popup"
                                              :preloads      [devtools.preload figwheel.preload]
                                              :main          repro.popup.main
                                              :optimizations :none
                                              :source-map    true}}}}}

             :figwheel
             {:figwheel       {:server-port 9500}
              :server-logfile ".figwheel.log"
              :repl           true}}

  :aliases {"fig"     ["with-profile" "+unpacked,+figwheel" "figwheel" "background" "popup"]
            "content" ["with-profile" "+unpacked-content-script" "cljsbuild" "auto" "content-script"]})
