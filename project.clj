(defproject webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.3"]
                 [org.postgresql/postgresql "42.2.12"]
                 [org.clojure/java.jdbc "0.3.2"]
                 [ring "1.8.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler  webapp.core/full-handler
         :init     webapp.core/on-init
         :port     4001
         :destroy  webapp.core/on-destroy}
  :repl-options {:init-ns webapp.core})
