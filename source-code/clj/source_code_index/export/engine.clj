
(ns source-code-index.export.engine
    (:require [io.api                            :as io]
              [source-code-index.core.messages   :as core.messages]
              [source-code-index.export.assemble :as export.assemble]
              [source-code-index.export.utils    :as export.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-changes-file!
  ; @ignore
  ;
  ; @param (maps in vector) index
  ; @param (map) options
  ;
  ; @return (maps in vector)
  [index {:keys [changes-filepath index-filepath source-paths] :as options}]
  (if-let [stored-index (io/read-edn-file index-filepath {:warn? false})]
          (let [derived-changes (export.utils/derive-changes stored-index index options)]
               (if (export.utils/any-declaration-changed? derived-changes)
                   (io/write-file! changes-filepath (export.assemble/assemble-changes-file index options derived-changes)
                                                    {:create? true :warn? true})
                   (println core.messages/NO-CHANGES-DETECTED-MESSAGE source-paths)))
          (println core.messages/MISSING-INDEX-FILE-WARNING source-paths))
  (-> index))

(defn update-index-file!
  ; @ignore
  ;
  ; @param (maps in vector) index
  ; @param (map) options
  [index {:keys [index-filepath]}]
  (letfn [(f0 [%] (-> % (export.utils/remove-outdated-source-paths index)
                        (export.utils/add-updated-source-paths     index)))]
         (io/update-edn-file! index-filepath f0)))
