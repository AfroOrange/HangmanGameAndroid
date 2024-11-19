package com.example.hangmangameandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.hangmangameandroid.R
import io.noties.markwon.Markwon
import java.io.BufferedReader
import java.io.InputStreamReader

class HelpActivity : BaseActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val markdownTextView = findViewById<TextView>(R.id.markdownTextView)
        if (markdownTextView == null) {
            Log.e("HelpActivity", "TextView with ID markdownTextView not found")
            return
        }

        try {
            val inputStream = resources.openRawResource(R.raw.about_app)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val markdownContent = reader.readText()
            reader.close()

            // Use Markwon to set the markdown content to the TextView
            val markwon = Markwon.create(this)
            markwon.setMarkdown(markdownTextView, markdownContent)
        } catch (e: Exception) {
            Log.e("HelpActivity", "Error reading markdown file", e)
        }
    }
}