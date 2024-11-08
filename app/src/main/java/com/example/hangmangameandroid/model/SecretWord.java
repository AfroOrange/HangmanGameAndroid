package com.example.hangmangameandroid.model;

public class SecretWord {
    private final String word;
    private String hiddenWord;

    public SecretWord(String word) {
        this.word = word;
        this.hiddenWord = generateHiddenWord(word);
    }

    private String generateHiddenWord(String word) {
        // Replace letters with underscores and keep spaces
        StringBuilder hiddenWordBuilder = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (c == ' ') {
                hiddenWordBuilder.append(" "); // Keep spaces as is
            } else {
                hiddenWordBuilder.append("_"); // Replace other characters with underscores
            }
        }
        return hiddenWordBuilder.toString();
    }

    // Getter for the hidden word
    public String getHiddenWord() {
        return hiddenWord;
    }

    // Setter for the hidden word (if needed for future modifications)
    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    // Update hidden word based on guesses
    public int guessLetter(char letter) {
        int appearances = 0;
        StringBuilder newHiddenWord = new StringBuilder(hiddenWord);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newHiddenWord.setCharAt(i, letter); // Replace underscores with the correct letter
                appearances++;
            }
        }

        hiddenWord = newHiddenWord.toString(); // Update the hidden word string
        return appearances;
    }

    // Getter for the original word
    public String getWord() {
        return word;
    }
}