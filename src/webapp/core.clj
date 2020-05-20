(ns webapp.core
  (:require [webapp.handlers :as handlers]
            [clojure.string]))

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

(defn not-found-middleware
  [handler]
  (fn
    [request]
    (or (handler request)
        {:status 404 :body (str "404 Not Found (with middleware!):" (:uri request))})))

(defn case-middleware
  [handler request]
  (let [request (update-in request [:uri] clojure.string/lower-case)
        response (handler request)]
    (if (string? (:body response))
      (update-in response [:body] clojure.string/capitalize)
      response)))

(defn wrap-case-middleware
  [handler]
  (fn
    [request]
    (case-middleware handler request)))

(defn exception-middleware
  [handler request]
  (try (handler request)
       (catch Throwable e
         {:status 500 :body (apply str (interpose "\n" (.getStackTrace e)))})))

(defn wrap-exception-middleware
  [handler]
  (fn
    [request]
    (exception-middleware handler request)))

(defn simple-log-middleware
  [handler]
  (fn
    [{:keys [uri] :as request}]
    (println "Request path:" uri)
    (handler request)))

(def full-handler
  (-> route-handler
      not-found-middleware
      wrap-case-middleware
      wrap-exception-middleware
      simple-log-middleware))