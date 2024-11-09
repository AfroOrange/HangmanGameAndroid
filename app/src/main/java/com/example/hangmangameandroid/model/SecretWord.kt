package com.example.hangmangameandroid.model

class SecretWord(private val word: String) {

    var hiddenWord: String
        private set

    init {
        this.hiddenWord = generateHiddenWord(word)
    }

    companion object {
        fun generateHiddenWord(word: String): String {
            // Replace letters with underscores and keep spaces
            val hiddenWordBuilder = StringBuilder()
            for (c in word.toCharArray()) {
                if (c == ' ') {
                    hiddenWordBuilder.append(" ") // Keep spaces as is
                } else {
                    hiddenWordBuilder.append("_") // Replace other characters with underscores
                }
            }
            return hiddenWordBuilder.toString()
        }
    }

    // Update hidden word based on guesses
    fun guessLetter(letter: Char): Int {
        var appearances = 0
        val newHiddenWord = StringBuilder(hiddenWord)

        for (i in word.indices) {
            if (word[i] == letter) {
                newHiddenWord.setCharAt(i, letter) // Replace underscores with the correct letter
                appearances++
            }
        }

        hiddenWord = newHiddenWord.toString() // Update the hidden word string
        return appearances
    }
}