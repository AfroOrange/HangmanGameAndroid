package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.utils.FileUtils
import com.example.hangmangameandroid.model.Users
import com.example.hangmangameandroid.utils.BackGroundMusic

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val nicknameInput = findViewById<EditText>(R.id.loginInputText)

        if (BackGroundMusic.isMusicEnabled(this)) {
            BackGroundMusic.initialize(this, R.raw.login_background_music_oria)
            BackGroundMusic.start()
        }

        // logic for the login button
        loginButton.setOnClickListener {
            // Define the regex pattern for the nickname
            val nicknamePattern = "^[a-zA-Z0-9_]{3,10}$"
            val nickname = nicknameInput.text.toString()

            // Validate the nickname against the pattern
            if (nickname.matches(nicknamePattern.toRegex())) {
                // Check if the nickname is already in the JSON file
                val users = FileUtils.readUsers(this)
                val userExists = users.any { it.name == nickname }

                if (userExists) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("NICKNAME", nickname)
                    startActivity(intent)
                } else {
                    addUserToJson(nickname)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("NICKNAME", nickname)  // Pass the nickname to MainActivity
                    startActivity(intent)
                }
            } else {
                // Show a message if the nickname is invalid
                Toast.makeText(this, "Invalid nickname. Please use 3-10 alphanumeric characters or underscores.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // retrieve the nickname from the Login menu and add it to the JSON file
    private fun addUserToJson(nickname: String) {
        val users = FileUtils.readUsers(this)
        val newUser = Users(name = nickname, score = 0)
        Log.d("LoginActivity", "Adding user: $newUser")
        users.add(newUser)
        FileUtils.writeUsers(this, users)
    }
}