# Schema Cartographer Cloud

<img width="2189" alt="cartographer-screenshot" src="https://user-images.githubusercontent.com/4416952/74056316-66b93000-49a7-11ea-90b5-72199edca388.png">

*Schema Cartographer* provides a means to visualize, navigate, create, edit and share the relationships that exist in your Datomic schema. The UI resides at [https://schema-cartographer.com](https://schema-cartographer.com)

![Clojure CI](https://github.com/JarrodCTaylor/schema-cartographer-cloud/workflows/Clojure%20CI/badge.svg)

## Usage

Clone this repository and from a repl in the core namespace evaluate the following:

``` clojure
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
```


## Copyright and License

Copyright © 2023 Jarrod Taylor

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
