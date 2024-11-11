package com.example.hangmangameandroid.model

class SecretWord(public val word: String) {

    var hiddenWord: String
        private set

    init {
        this.hiddenWord = generateHiddenWord(word)
    }

    companion object {
        fun generateHiddenWord(word: String): String {
            val hiddenWordBuilder = StringBuilder()
            for (c in word.toCharArray()) {
                if (c == ' ') {
                    hiddenWordBuilder.append(" ")
                } else {
                    hiddenWordBuilder.append("_")
                }
            }
            return hiddenWordBuilder.toString()
        }
    }

    fun guessWord(word: String): Boolean {
        if (word == this.word) {
            hiddenWord = this.word
            return true
        } else {
            return false
        }
    }

    fun guessLetter(letter: Char): Int {
        var appearances = 0
        val newHiddenWord = StringBuilder(hiddenWord)

        for (i in word.indices) {
            if (word[i] == letter) {
                newHiddenWord.setCharAt(i, letter)
                appearances++
            }
        }

        hiddenWord = newHiddenWord.toString()
        return appearances
    }

    fun updateHiddenWord() {
        hiddenWord = hiddenWord
    }
}