package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nickname = intent.getStringExtra("NICKNAME")
        if (nickname != null) {
            val nicknameTextView = findViewById<TextView>(R.id.playerNickname)
            nicknameTextView.text = "Player: $nickname"
        }

        val resetButton = findViewById<Button>(R.id.resetGameButton)
        resetButton.setOnClickListener {
            findViewById<TextView>(R.id.livesField).text = "Lives:♡♡♡♡♡♡♡♡♡ "
            findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)
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
}