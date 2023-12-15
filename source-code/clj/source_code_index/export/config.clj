
(ns source-code-index.export.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @constant (regex pattern)
(def VERSION-PATTERN #"(?<=\n\#\#\#\s\[).*(?=\]\s\-\s[\d]{4,4}\-[\d]{2,2}\-[\d]{2,2})")
