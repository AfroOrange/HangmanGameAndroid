package com.example.hangmangameandroid.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.example.hangmangameandroid.R

object BackGroundMusic {

    private const val PREFS_NAME = "music_prefs"
    private const val MUSIC_ENABLED_KEY = "music_enabled"
    private var mediaPlayer: MediaPlayer? = null

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.login_background_music_oria)
            mediaPlayer?.isLooping = true
        }
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.prepare()
    }

    fun isMusicEnabled(context: Context): Boolean {
        val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(MUSIC_ENABLED_KEY, true) // Default value is true
    }

    fun setMusicEnabled(context: Context, isEnabled: Boolean) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean(MUSIC_ENABLED_KEY, isEnabled)
            apply()
        }
    }
    fun isInitialized(): Boolean {
        return mediaPlayer != null
    }
}