package com.example.hangmangameandroid.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.example.hangmangameandroid.R

object BackGroundMusic {

    private const val PREFS_NAME = "music_prefs"
    private const val MUSIC_ENABLED_KEY = "music_enabled"
    var mediaPlayer: MediaPlayer? = null

    fun initialize(context: Context, trackResId: Int) {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer.create(context, trackResId)
        mediaPlayer?.isLooping = true
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
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
}