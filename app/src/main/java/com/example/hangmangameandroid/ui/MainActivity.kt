package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    // In MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nickname =
            intent.getStringExtra("NICKNAME")  // Get the nickname passed from LoginActivity
        if (nickname != null) {
            // Do something with the nickname (e.g., display it in a TextView)
            val welcomeTextView = findViewById<TextView>(R.id.welcomeText)
            welcomeTextView.text = "Welcome, $nickname!"  // Set the welcome message
        }

        // actions for the buttons
        val playButton = findViewById<Button>(R.id.newGameButton)
        playButton.setOnClickListener {
            // Start the GameActivity
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("NICKNAME", nickname)  // Pass the nickname to GameActivity
            startActivity(intent)
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
            exitProcess(0)
        }

        // prevents the user from going back to the previous activity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing or provide custom behavior
            }
        })
    }
}

