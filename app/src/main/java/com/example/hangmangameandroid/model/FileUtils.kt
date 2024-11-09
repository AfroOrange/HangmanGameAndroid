package com.example.hangmangameandroid.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object FileUtils {
    private const val USER_FILE_NAME = "users.json"
    private const val WORD_FILE_NAME = "words.json"

    fun readUsers(context: Context): MutableList<Users> {
        val file = File(context.filesDir, USER_FILE_NAME)
        if (!file.exists()) {
            return mutableListOf()
        }

        val reader = FileReader(file)
        val type = object : TypeToken<MutableList<Users>>() {}.type
        return Gson().fromJson(reader, type)
    }

    fun writeUsers(context: Context, users: MutableList<Users>) {
        val file = File(context.filesDir, USER_FILE_NAME)
        val writer = FileWriter(file)
        Gson().toJson(users, writer)
        writer.flush()
        writer.close()
    }

    fun readWords(context: Context): MutableList<String> {
        val file = File(context.filesDir, WORD_FILE_NAME)
        if (!file.exists()) {
            return mutableListOf()
        }

        val reader = FileReader(file)
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(reader, type)
    }

    fun writeWords(context: Context, words: MutableList<String>) {
        val file = File(context.filesDir, WORD_FILE_NAME)
        val writer = FileWriter(file)
        Gson().toJson(words, writer)
        writer.flush()
        writer.close()
    }
}