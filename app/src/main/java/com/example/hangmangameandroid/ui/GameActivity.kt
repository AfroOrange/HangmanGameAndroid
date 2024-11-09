// GameActivity.kt
package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.model.SecretWord
import com.example.hangmangameandroid.utils.FileUtils
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var wordList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nickname = intent.getStringExtra("NICKNAME")
        if (nickname != null) {
            val nicknameTextView = findViewById<TextView>(R.id.nicknameField)
            nicknameTextView.text = "Player: $nickname"
        }

        val newGame = findViewById<Button>(R.id.newGameButton)
        newGame.setOnClickListener {
            startGame()
        }

        // this code block is used to prevent the user from going back to the previous activity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        val mainMenuButton = findViewById<Button>(R.id.mainMenuButton)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Read the word list from words.json file
        try {
            wordList = FileUtils.readWords(this)
            Log.d("GameActivity", "Word list loaded: $wordList")
        } catch (e: Exception) {
            wordList = emptyList()
            Log.e("GameActivity", "Error loading word list: ${e.message}", e)
        }
    }

    private fun startGame() {
        findViewById<TextView>(R.id.livesField).text = "Lives: \uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4"
        findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)

        try {
            // Generate a random word from the list of words
            val word = getRandomWord()
            Log.d("GameActivity", "Random word generated: $word")

            // Create an instance of SecretWord and get the hidden word
            val secretWord = SecretWord(word)
            val hiddenWord = secretWord.hiddenWord
            Log.d("GameActivity", "Hidden word generated: $hiddenWord")
            findViewById<TextView>(R.id.hiddenWordField).text = hiddenWord
        } catch (e: Exception) {
            Log.e("GameActivity", "Error in startGame: ${e.message}", e)
        }
    }

    private fun getRandomWord(): String {
        if (wordList.isNotEmpty()) {
            val randomIndex = Random.nextInt(wordList.size)
            return wordList[randomIndex]
        } else {
            throw IllegalStateException("Word list is empty")
        }
    }
}