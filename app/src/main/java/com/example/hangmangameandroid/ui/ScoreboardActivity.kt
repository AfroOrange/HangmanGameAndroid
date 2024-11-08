// ScoreboardActivity.kt
package com.example.hangmangameandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.model.FileUtils

class ScoreboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        initScoreboard()
    }

    private fun initScoreboard() {
        val scoreboard = findViewById<RecyclerView>(R.id.scoreboardTable)
        val users = FileUtils.readUsers(this)

        // Initialize RecyclerView with the users list
        scoreboard.layoutManager = LinearLayoutManager(this)
        scoreboard.adapter = UserAdapter(users)
    }
}