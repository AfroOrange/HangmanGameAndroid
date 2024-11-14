package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.utils.FileUtils
import com.example.hangmangameandroid.model.Users

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val nicknameInput = findViewById<EditText>(R.id.loginInputText)

        // logic for the login button
        loginButton.setOnClickListener {
            val nickname = nicknameInput.text.toString()

            if (nickname.isNotEmpty()) {
                addUserToJson(nickname)  // Add user to JSON file
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NICKNAME", nickname)  // Pass the nickname to MainActivity
                startActivity(intent)
            } else {
                // Show a message if the nickname is empty
                Toast.makeText(this, "Please enter a nickname", Toast.LENGTH_SHORT).show()
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