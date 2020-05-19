(ns webapp.core
  (:require [webapp.handlers :as handlers]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn example-handler
  [request]
  {:headers {"Set-cookie" "test=1"}
   :status 301
   :body (java.io.File. "test.txt")})

(defn on-init []
  (println "Initializing sample webapp!"))

(defn on-destroy []
  (println "Destroying sample webapp!"))

(defn test1-handler
  [request]
  {:body "test1"})

(defn test2-handler
  [request]
  {:status 301
   :headers {"Location" "http://github.com/ring-clojure/ring"}})

(defn route-handler
  [request]
  (condp = (:uri request)
    "/test1" (test1-handler request)
    "/test2" (test2-handler request)
    "/test3" (handlers/handler3 request)
    (example-handler request)))