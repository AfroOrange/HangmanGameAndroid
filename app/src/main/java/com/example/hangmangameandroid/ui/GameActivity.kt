package com.example.hangmangameandroid.ui

import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nickname =
            intent.getStringExtra("NICKNAME")  // Get the nickname passed from MainActivity
        if (nickname != null) {
            // Do something with the nickname (e.g., display it in a TextView)
            val nickname = findViewById<TextView>(R.id.playerNickname)
            nickname.text = "Player: $nickname"  // Set the player id
        }

        // actions for the buttons
        val resetButton = findViewById<Button>(R.id.resetGameButton)
        resetButton.setOnClickListener {
            // Reset the game
            findViewById<TextView>(R.id.livesField).setText("Lives: 9")
            findViewById<ImageView>(R.id.hangmanImage).setImageResource(R.drawable.image1)
        }
    }
}