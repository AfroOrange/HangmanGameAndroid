package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.utils.FileUtils

class MainActivity : AppCompatActivity() {

    // In MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nickname = intent.getStringExtra("NICKNAME")  // Get the nickname passed from LoginActivity
        if (nickname != null) {
            // Do something with the nickname (e.g., display it in a TextView)
            val welcomeTextView = findViewById<TextView>(R.id.welcomeText)
            welcomeTextView.text = "Welcome, $nickname!"  // Set the welcome message
        }

        // actions for the buttons
        val playButton = findViewById<Button>(R.id.newGameButton)
        playButton.setOnClickListener {

            //check if there are words in the json before playing the game
            val words = FileUtils.readWords(this)
            if (words.isEmpty()) {
                // Show a message if there are no words
                val toast = "Please add words in the Words menu"
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else {
                // Start the GameActivity
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("NICKNAME", nickname)  // Pass the nickname to GameActivity
                startActivity(intent)
            }
        }

        val scoreboardButton = findViewById<Button>(R.id.scoreboardButton)
        scoreboardButton.setOnClickListener {
            // Start the ScoreboardActivity
            val intent = Intent(this, ScoreboardActivity::class.java)
            startActivity(intent)
        }

        val wordsButton = findViewById<Button>(R.id.wordsButton)
        wordsButton.setOnClickListener {
            // Start the WordsActivity
            val intent = Intent(this, WordsActivity::class.java)
            startActivity(intent)
        }

        val exitButton = findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {
            // Exit the app
            finishAffinity()
        }

        // prevents the user from going back to the previous activity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}

