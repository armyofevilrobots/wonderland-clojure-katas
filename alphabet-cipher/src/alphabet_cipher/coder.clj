(ns alphabet-cipher.coder)

(def alphabet (mapv char (range (int \a) (+ 1 (int \z)))))

(def cipher (mapv (fn [x] 
                    (vec (concat (drop x alphabet) (take x alphabet)))) 
                  (range 26)))

(defn char-ofs [the-char]
  (- (int the-char) (int \a)))

(defn char-ofs-inv [the-char]
  (mod (- (+ 1 (int \z)) (int the-char)) 26))

(defn charsub [key-ofs the-char the-key]
  (get-in cipher [(key-ofs the-key) (char-ofs the-char)]))

(defn apply-cipher [key-ofs-fun keyword message]
  (apply str 
         (for [ofs (range (count message))]
           ((partial charsub key-ofs-fun)
            (nth message ofs)
            (nth keyword (mod ofs (count keyword)))))))

(def encode (partial apply-cipher char-ofs))
(def decode (partial apply-cipher char-ofs-inv))

