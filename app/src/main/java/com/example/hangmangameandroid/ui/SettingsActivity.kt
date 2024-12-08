package com.example.hangmangameandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.model.TrackList
import com.example.hangmangameandroid.utils.BackGroundMusic

class SettingsActivity : BaseActivity() {

    private var currentSong: TrackList? = null

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
        val savedTrack = sharedPreferences.getString("currentSong", TrackList.TRACK1.name)
        currentSong = TrackList.valueOf(savedTrack ?: TrackList.TRACK1.name)

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
            val trackResId = when (currentSong) {
                TrackList.TRACK1 -> R.raw.login_background_music_oria
                TrackList.TRACK2 -> R.raw.ingame_background_music_oria
                else -> R.raw.login_background_music_oria
            }
            if (BackGroundMusic.mediaPlayer == null || !BackGroundMusic.mediaPlayer!!.isPlaying) {
                BackGroundMusic.initialize(this, trackResId)
                BackGroundMusic.start()
            }
        } else {
            musicToggleText.text = "Music: OFF"
            trackText.isEnabled = false
            volumeText.isEnabled = false
            trackSelector.isEnabled = false
            volumeSlider.isEnabled = false
            BackGroundMusic.stop()
        }

        // Populate the spinner with the TrackList enum values
        val trackListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            TrackList.entries.toTypedArray())
        trackListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        trackSelector.adapter = trackListAdapter

        // Set the spinner to the saved track
        trackSelector.setSelection(TrackList.entries.indexOf(currentSong))

        // Handle spinner selection
        trackSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTrack = TrackList.entries[position]
                currentSong = selectedTrack // Update the currentSong variable
                val editor = sharedPreferences.edit()
                editor.putString("currentSong", selectedTrack.name)
                editor.apply()
                when (selectedTrack) {
                    TrackList.TRACK1 -> {
                        BackGroundMusic.stop() // Stop the current track
                        BackGroundMusic.initialize(this@SettingsActivity, R.raw.login_background_music_oria)
                    }
                    TrackList.TRACK2 -> {
                        BackGroundMusic.stop() // Stop the current track
                        BackGroundMusic.initialize(this@SettingsActivity, R.raw.ingame_background_music_oria)
                    }
                }
                if (isMusicEnabled) {
                    BackGroundMusic.start()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Retrieve the saved volume level from SharedPreferences
        val savedVolume = sharedPreferences.getInt("volumeLevel", 50) // Default volume is 50
        volumeSlider.progress = savedVolume
        BackGroundMusic.mediaPlayer?.setVolume(savedVolume.toFloat() / 100, savedVolume.toFloat() / 100)

        // Bind the slider to the volume control
        volumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                BackGroundMusic.mediaPlayer?.setVolume(progress.toFloat() / 100, progress.toFloat() / 100)
                val editor = sharedPreferences.edit()
                editor.putInt("volumeLevel", progress)
                editor.apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })

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
                val trackResId = when (currentSong) {
                    TrackList.TRACK1 -> R.raw.login_background_music_oria
                    TrackList.TRACK2 -> R.raw.ingame_background_music_oria
                    else -> R.raw.login_background_music_oria // Default track
                }
                if (BackGroundMusic.mediaPlayer == null || !BackGroundMusic.mediaPlayer!!.isPlaying) {
                    BackGroundMusic.initialize(this, trackResId)
                    BackGroundMusic.start()
                }
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