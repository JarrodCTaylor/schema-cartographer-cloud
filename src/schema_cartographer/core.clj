(ns schema-cartographer.core
  (:require
    [clojure.pprint :as pp]
    [datomic.client.api :as d]
    [schema-cartographer.queries :as queries]
    [schema-cartographer.annotation-audit :refer [log-schema-audit]]
    [schema-cartographer.explorer :refer [explore]]
    [schema-cartographer.schema :refer [data-map]]))

 (defn save-schema-edn [db schema-file-name]
   (let [raw-schema (queries/annotated-schema db)
         schema-data (data-map raw-schema)
         output-location schema-file-name]
     (spit output-location (with-out-str (pp/pprint schema-data)))
     (println (str "== " output-location " successfully saved. =="))))

(defn save-explore-schema-edn [db schema-file-name ref-search-limit]
  (let [_ (println "== Querying Schema ==\n")
        raw-schema (queries/unannotated-schema {:db db})
        _ (println "-- Exploring Database --\n")
        schema-data (explore db raw-schema ref-search-limit)
        output-location schema-file-name]
    (spit output-location (with-out-str (pp/pprint schema-data)))
    (println (str "== " output-location " successfully saved. =="))))

(comment
    (def arg-map {:server-type :ion
                  :region "us-east-1"
                  :system "<<system>>"                     ; <stack-name> also called <system-name>
                  :endpoint "<<ClientApiGatewayEndpoint>>" ; ClientApiGatewayEndpoint can be found in the outputs tab of compute stack in cloudformation
                  :creds-profile "<<AWS CREDS PROFILE>>"})

    (def client (d/client arg-map))
    (def conn (d/connect client {:db-name "<<YOUR-DB-NAME>>"}))
    (def db (d/db conn))

    ;; Arguments to the save-explore-schema-edn
    ;; 1) `db`
    ;; 2) `path` to save the resulting schema file to
    ;; 3) `ref-search-limit` This is the number of referenced entities to inspect when creating relationships maps can be `nil` for no limit. If `nil` this could take a LONG time for large databases
    (save-explore-schema-edn db "resources/your-systems-schema.edn" 2000000))