package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.adapters.WordsAdapter
import com.example.hangmangameandroid.model.SecretWord
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nickname = intent.getStringExtra("NICKNAME")
        if (nickname != null) {
            val nicknameTextView = findViewById<TextView>(R.id.playerNickname)
            nicknameTextView.text = "Player: $nickname"
        }

        val newGame = findViewById<Button>(R.id.newGameButton)
        newGame.setOnClickListener {
            startGame()
        }

        // this code block is used to prevent the user from going back to the previous activity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing or provide custom behavior
            }
        })

        val mainMenuButton = findViewById<Button>(R.id.mainMenuButton)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startGame() {
        findViewById<TextView>(R.id.livesField).text = "Lives: ♡♡♡♡♡♡♡♡♡"
        findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)

        // Generate a random word from the list of words
        val word = getRandomWord()

        // Generate the hidden word using the random word
        val secretWord = SecretWord(getRandomWord())
        val hiddenWord = secretWord.generateHiddenWord()

        val wordTextView = findViewById<TextView>(R.id.hiddenWordField)
        wordTextView.text = hiddenWord
    }

    private fun getRandomWord(): String {
        val wordsListView = findViewById<ListView>(R.id.wordsList)
        val adapter = wordsListView.adapter as WordsAdapter
        val wordList = adapter.getWords() // Assuming WordsAdapter has a method to get the list of words

        if (wordList.isNotEmpty()) {
            val randomIndex = Random.nextInt(wordList.size)
            return wordList[randomIndex]
        } else {
            throw IllegalStateException("Word list is empty")
        }
    }
}