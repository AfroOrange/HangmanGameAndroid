package com.example.hangmangameandroid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val nicknameInput = findViewById<EditText>(R.id.loginInputText)

        loginButton.setOnClickListener {
            val nickname = nicknameInput.text.toString()

            if (nickname.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NICKNAME", nickname)  // Pass the nickname to MainActivity
                startActivity(intent)
            } else {
                // Show a message if the nickname is empty
                Toast.makeText(this, "Please enter a nickname", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
