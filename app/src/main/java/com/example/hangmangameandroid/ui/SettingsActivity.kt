package com.example.hangmangameandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.utils.BackGroundMusic

class SettingsActivity : BaseActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode", "MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val lightThemeButton = findViewById<ImageButton>(R.id.lightThemeButton)
        val darkThemeButton = findViewById<ImageButton>(R.id.darkThemeButton)
        val trackText = findViewById<TextView>(R.id.trackText)
        val volumeText = findViewById<TextView>(R.id.volumeText)
        val trackSelector = findViewById<Spinner>(R.id.trackSelector)
        val volumeSlider = findViewById<SeekBar>(R.id.volumeSlider)
        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        val musicToggleText = findViewById<TextView>(R.id.musicToggleText)
        val helpButton = findViewById<ImageButton>(R.id.helpButton)

        // Retrieve the current theme selection from SharedPreferences
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        val isMusicEnabled = BackGroundMusic.isMusicEnabled(this)

        // Set initial selected state based on current theme
        if (isDarkTheme) {
            darkThemeButton.isSelected = true
            lightThemeButton.isSelected = false
        } else {
            lightThemeButton.isSelected = true
            darkThemeButton.isSelected = false
        }

        // Restore music switch state
        musicSwitch.isChecked = isMusicEnabled
        if (isMusicEnabled) {
            musicToggleText.text = "Music: ON"
            trackText.isEnabled = true
            volumeText.isEnabled = true
            trackSelector.isEnabled = true
            volumeSlider.isEnabled = true
            BackGroundMusic.initialize(this)
            BackGroundMusic.start()
        } else {
            musicToggleText.text = "Music: OFF"
            trackText.isEnabled = false
            volumeText.isEnabled = false
            trackSelector.isEnabled = false
            volumeSlider.isEnabled = false
            BackGroundMusic.stop()
        }

        // light theme listener
        lightThemeButton.setOnClickListener {
            lightThemeButton.isSelected = true
            darkThemeButton.isSelected = false
            switchTheme(false)
        }

        // dark theme listener
        darkThemeButton.setOnClickListener {
            lightThemeButton.isSelected = false
            darkThemeButton.isSelected = true
            switchTheme(true)
        }

        // Music switch listener
        musicSwitch.setOnClickListener {
            if (musicSwitch.isChecked) {
                musicToggleText.text = "Music: ON"
                trackText.isEnabled = true
                volumeText.isEnabled = true
                trackSelector.isEnabled = true
                volumeSlider.isEnabled = true
                BackGroundMusic.initialize(this)
                BackGroundMusic.start()
                BackGroundMusic.setMusicEnabled(this, true)
            } else {
                musicToggleText.text = "Music: OFF"
                trackText.isEnabled = false
                volumeText.isEnabled = false
                trackSelector.isEnabled = false
                volumeSlider.isEnabled = false
                BackGroundMusic.stop()
                BackGroundMusic.setMusicEnabled(this, false)
            }
        }

        // Help button listener
        helpButton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to switch the theme to all activities
    private fun switchTheme(isDarkTheme: Boolean) {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDarkTheme", isDarkTheme)
        editor.apply()
        recreate()
    }
}