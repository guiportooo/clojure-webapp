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

(defn test4-handler
  [request]
  (throw (RuntimeException. "Error!")))

(defn route-handler
  [request]
  (condp = (:uri request)
    "/test1" (test1-handler request)
    "/test2" (test2-handler request)
    "/test3" (handlers/handler3 request)
    "/test4" (test4-handler request)
    nil))

(defn wrapping-handler
  [request]
  (try
    (if-let [resp (route-handler request)]
      resp
      {:status 404 :body (str "Not found: " (:uri request))})
    (catch Throwable e
      {:status 500 :body (apply str (interpose "\n" (.getStackTrace e)))})))