(ns webapp.db
  (:require [korma.db :as korma-db]
            [korma.core :as korma]))

(def postgresql-db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/clojure_blog"
   :user "postgres"
   :password "docker"})

(korma-db/defdb korma-pool postgresql-db)

(korma/defentity entries)