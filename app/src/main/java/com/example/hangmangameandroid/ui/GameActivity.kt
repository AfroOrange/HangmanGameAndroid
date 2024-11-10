// GameActivity.kt
package com.example.hangmangameandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.model.ImagesContainer
import com.example.hangmangameandroid.model.SecretWord
import com.example.hangmangameandroid.utils.FileUtils
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var wordList: List<String>
    private var guessedList = mutableListOf(String())
    private lateinit var secretWord: SecretWord
    private lateinit var imagesContainer : ImagesContainer
    private var imageIndex = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nickname = intent.getStringExtra("NICKNAME")
        if (nickname != null) {
            val nicknameTextView = findViewById<TextView>(R.id.nicknameField)
            nicknameTextView.text = "Player: $nickname"
        }

        imagesContainer = ImagesContainer(this)

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

        // handle the letter button click
        val letterButton = findViewById<Button>(R.id.letterButton)
        letterButton.setOnClickListener {
            tryLetter()
        }
    }

    // FUNCTIONS

    //logic for guessing the word
    private fun tryLetter() {
        val letterInput = findViewById<TextView>(R.id.wordGuesserField)
        val letterGuessed = letterInput.text.toString().firstOrNull()

        if (letterGuessed != null) {
            if (!guessedList.contains(letterGuessed.toString())) {
                if (secretWord.guessLetter(letterGuessed) == 0) {
                    imageIndex += 1
                    Log.d("!=!=!=!", "ImageIndex at the moment: $imageIndex")
                    if (imageIndex <= 9) {
                        findViewById<ImageView>(R.id.hangmanImage).setImageDrawable(imagesContainer.getImage(imageIndex))
                    }

                    if (imageIndex == 9) {
                        // gameOverAlert()
                        findViewById<TextView>(R.id.hiddenWordField).text = "The word was: ${secretWord.word}"
                    }
                } else {
                    secretWord.updateHiddenWord()
                    findViewById<TextView>(R.id.hiddenWordField).text = secretWord.hiddenWord

                    if (!secretWord.hiddenWord.contains('_')) {
//                        gameOverAlert()
                        findViewById<TextView>(R.id.hiddenWordField).text = "You won!"
                    }
                }
                guessedList.add(letterGuessed.toString())
                findViewById<TextView>(R.id.guessedWordList).text = guessedList.joinToString(separator = " ")
                Log.d("GameActivity", "Letter button clicked: $letterGuessed")
            } else {
                Toast.makeText(this, "You have already guessed this letter", Toast.LENGTH_SHORT).show()
                Log.d("GameActivity", "Letter already guessed: $letterGuessed")
            }
        } else {
            Toast.makeText(this, "Please enter a valid letter", Toast.LENGTH_SHORT).show()
            Log.d("GameActivity", "No letter entered")
        }
    }

    private fun gameOverAlert() {
        TODO("Not yet implemented")
    }

    //logic for starting a new gam
    private fun startGame() {
        findViewById<TextView>(R.id.livesField).text = "Lives: \uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4"
        findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)

        try {
            val word = getRandomWord()
            Log.d("GameActivity", "Random word generated: $word")

            secretWord = SecretWord(word)
            val hiddenWord = secretWord.hiddenWord
            Log.d("GameActivity", "Hidden word generated: $hiddenWord")
            findViewById<TextView>(R.id.hiddenWordField).text = hiddenWord
        } catch (e: Exception) {
            Log.e("GameActivity", "Error in startGame: ${e.message}", e)
        }

        findViewById<Button>(R.id.letterButton).visibility = Button.VISIBLE
        findViewById<Button>(R.id.solveButton).visibility = Button.VISIBLE
    }

    //logic for getting a random word from the word list
    private fun getRandomWord(): String {
        if (wordList.isNotEmpty()) {
            val randomIndex = Random.nextInt(wordList.size)
            return wordList[randomIndex]
        } else {
            throw IllegalStateException("Word list is empty")
        }
    }
}