package com.example.hangmangameandroid.ui

import android.os.Bundle
import android.widget.ImageButton
import com.example.hangmangameandroid.R

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val lightThemeButton = findViewById<ImageButton>(R.id.lightThemeButton)
        val darkThemeButton = findViewById<ImageButton>(R.id.darkThemeButton)

        // Retrieve the current theme selection from SharedPreferences
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)

        // Set initial selected state based on current theme
        if (isDarkTheme) {
            darkThemeButton.isSelected = true
            lightThemeButton.isSelected = false
        } else {
            lightThemeButton.isSelected = true
            darkThemeButton.isSelected = false
        }

        // Click listeners to change theme
        lightThemeButton.setOnClickListener {
            lightThemeButton.isSelected = true
            darkThemeButton.isSelected = false
            switchTheme(false)
        }

        darkThemeButton.setOnClickListener {
            lightThemeButton.isSelected = false
            darkThemeButton.isSelected = true
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
