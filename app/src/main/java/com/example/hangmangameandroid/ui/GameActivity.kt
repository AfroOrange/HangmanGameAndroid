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

class GameActivity : BaseActivity() {

    // Variables
    private lateinit var wordList: List<String>
    private var guessedList = mutableListOf<String>()
    private lateinit var secretWord: SecretWord
    private lateinit var imagesContainer: ImagesContainer
    private var imageIndex = 1

    // UI elements
    private lateinit var mainMenuButton: Button
    private lateinit var letterButton: Button
    private lateinit var solveButton: Button
    private lateinit var wordGuesserField: EditText
    private lateinit var newGameButton: Button
    private lateinit var healthBar: TextView
    private lateinit var scoreField: TextView
    private lateinit var scoreLabel: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Initialize the UI elements
        letterButton = findViewById(R.id.letterButton)
        solveButton = findViewById(R.id.solveButton)
        wordGuesserField = findViewById(R.id.wordGuesserField)
        newGameButton = findViewById(R.id.newGameButton)
        healthBar = findViewById(R.id.healthBar)
        scoreField = findViewById(R.id.scoreField)
        mainMenuButton = findViewById(R.id.mainMenuButton)
        scoreLabel = findViewById(R.id.scoreLabel)

        // Initialize the images container
        imagesContainer = ImagesContainer(this)

        // Get the nickname passed from the MainActivity
        val nickname = intent.getStringExtra("NICKNAME")
        if (nickname != null) {
            val nicknameTextView = findViewById<TextView>(R.id.nicknameField)
            nicknameTextView.text = "Player: $nickname"
        }

        // Read the word list from words.json file to use in the game
        try {
            wordList = FileUtils.readWords(this)
            Log.d("GameActivity", "Word list loaded: $wordList")
        } catch (e: Exception) {
            wordList = emptyList()
            Log.e("GameActivity", "Error loading word list: ${e.message}", e)
        }

        // Prevent the user from going back to the previous activity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // prevent the user from going back to the previous activity
            }
        })

        // BUTTONS
        newGameButton.setOnClickListener {
            startGame()
        }

        // button to go back to the main menu
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NICKNAME", nickname)
            startActivity(intent)
        }

        // button to guess a letter
        letterButton.setOnClickListener {
            tryLetter()
        }

        // button to try solve the word
        solveButton.setOnClickListener {
            trySolve()
        }
    }

    // Logic for guessing the word
    private fun trySolve() {
        val wordInput = findViewById<EditText>(R.id.wordGuesserField)
        val wordGuessed = wordInput.text.toString()
        var score = scoreField.text.toString().toInt()

        if (secretWord.guessWord(wordGuessed)) {
            score += 50
            scoreField.text = score.toString()
            findViewById<TextView>(R.id.hiddenWordField).text = "You won!"
            gameOver()

        } else {

            // subtract 1 from the score
            score -= 1
            if (score >= 1) {
                scoreField.text = score.toString()
            }

            // Update the index and the health bar
            healthBarUpdate()
            imageIndex += 1
            findViewById<ImageView>(R.id.hangmanImage).setImageDrawable(imagesContainer.getImage(imageIndex))

            // if the image index is 9, the game is over
            if (imageIndex == 9) {
                gameOver()
                Toast.makeText(this, "You lost! The word was: ${secretWord.word}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Logic for guessing the word
    private fun tryLetter() {
        val letterInput = findViewById<EditText>(R.id.wordGuesserField)
        val letterGuessed = letterInput.text.toString().firstOrNull()
        var score = scoreField.text.toString().toInt()

        if (letterGuessed != null) {
            if (!guessedList.contains(letterGuessed.toString())) {
                if (secretWord.guessLetter(letterGuessed) == 0) {
                    // Update the index and the health bar
                    imageIndex += 1
                    healthBarUpdate()

                    Log.d("!=!=!=!", "ImageIndex at the moment: $imageIndex")
                    if (imageIndex <= 9) {
                        findViewById<ImageView>(R.id.hangmanImage).setImageDrawable(imagesContainer.getImage(imageIndex))
                    }

                    if (imageIndex == 9) {
                        gameOver()
                        findViewById<TextView>(R.id.hiddenWordField).text = "The word was: ${secretWord.word}"
                    }
                } else {
                    secretWord.updateHiddenWord()
                    findViewById<TextView>(R.id.hiddenWordField).text = secretWord.hiddenWord
                    score += 1

                    if (!secretWord.hiddenWord.contains('_')) {
                        findViewById<TextView>(R.id.hiddenWordField).text = "You won!"
                        gameOver()
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
        // Clear the input field
        wordGuesserField.text.clear()
        scoreField.text = score.toString()
    }

    // Logic for ending the game
    private fun gameOver() {
        letterButton.visibility = Button.INVISIBLE
        solveButton.visibility = Button.INVISIBLE
        wordGuesserField.visibility = EditText.INVISIBLE
        newGameButton.visibility = Button.VISIBLE
    }

    // Logic for starting a new game
    private fun startGame() {
        resetFields()

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

        // Clear the guessed list
        guessedList.clear()
        findViewById<TextView>(R.id.guessedWordList).text = ""

        // Set the image index to 1
        imageIndex = 1

        // Make the letter button, solve button, and word guesser field visible
        letterButton.visibility = Button.VISIBLE
        solveButton.visibility = Button.VISIBLE
        wordGuesserField.visibility = EditText.VISIBLE

        // Make the new game button invisible
        newGameButton.visibility = Button.INVISIBLE
    }

    // Logic for getting a random word from the word list
    private fun getRandomWord(): String {
        if (wordList.isNotEmpty()) {
            val randomIndex = Random.nextInt(wordList.size)
            return wordList[randomIndex]
        } else {
            throw IllegalStateException("Word list is empty")
        }
    }

    // Logic for updating the health bar
    private fun healthBarUpdate() {

        // Check if the health bar text is not empty before trimming the last character
        if (healthBar.text.isNotEmpty()) {
            healthBar.text = healthBar.text.dropLast(2) // Drop the last heart emoji
        }
    }

    private fun resetFields() {
        healthBar.text = "\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4\uD83D\uDDA4"
        findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)
        scoreField.text = "0"
        scoreLabel.visibility = TextView.VISIBLE

    }
}