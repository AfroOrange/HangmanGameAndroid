package com.example.hangmangameandroid.ui

import android.os.Bundle
import android.widget.ImageButton
import com.example.hangmangameandroid.R

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ImageButton>(R.id.lightThemeButton).setOnClickListener {
            switchTheme(false)
        }

        findViewById<ImageButton>(R.id.darkThemeButton).setOnClickListener {
            switchTheme(true)
        }
    }

    private fun switchTheme(isDarkTheme: Boolean) {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDarkTheme", isDarkTheme)
        editor.apply()
        recreate() // Restart the activity to apply the new theme
    }
}