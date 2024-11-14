// WordsActivity.kt
package com.example.hangmangameandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.adapters.WordsAdapter
import com.example.hangmangameandroid.utils.FileUtils

class WordsActivity : BaseActivity() {

    private lateinit var addWordButton: Button
    private lateinit var addWordField: EditText
    private lateinit var wordsListView: ListView
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var wordList: MutableList<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        addWordButton = findViewById(R.id.addWordButton)
        addWordField = findViewById(R.id.addWordField)
        wordsListView = findViewById(R.id.wordsList)

        try {
            wordList = FileUtils.readWords(this)
            Log.d("WordsActivity", "Word list loaded: $wordList")
        } catch (e: Exception) {
            Log.e("WordsActivity", "Error loading word list: ${e.message}", e)
            wordList = mutableListOf()
        }

        wordsAdapter = WordsAdapter(this, wordList)
        wordsListView.adapter = wordsAdapter

        // Register ListView for context menu
        registerForContextMenu(wordsListView)

        addWordButton.setOnClickListener {
            val word = addWordField.text.toString()
            if (word.isNotEmpty()) {
                addWordToJson(word)
                wordList.add(word)
                wordsAdapter.notifyDataSetChanged()
            }
        }
    }

    // Add word to JSON file
    private fun addWordToJson(word: String) {
        try {
            val words = FileUtils.readWords(this)
            words.add(word)
            FileUtils.writeWords(this, words)
            Log.d("WordsActivity", "Word added to JSON: $word")
        } catch (e: Exception) {
            Log.e("WordsActivity", "Error adding word to JSON: ${e.message}", e)
        }
    }

    // Remove word from JSON file
    private fun removeWordFromJson(word: String) {
        try {
            val words = FileUtils.readWords(this)
            words.remove(word)
            FileUtils.writeWords(this, words)
            Log.d("WordsActivity", "Word removed from JSON: $word")
        } catch (e: Exception) {
            Log.e("WordsActivity", "Error removing word from JSON: ${e.message}", e)
        }
    }

    // Register ListView for context menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.wordsList) {
            menu?.add(0, v.id, 0, "Delete")
        }
    }

    // Handle context menu item selection
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Delete" -> {
                // Get the position of the item to delete
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val position = info.position
                val word = wordList[position]
                wordList.removeAt(position)
                wordsAdapter.notifyDataSetChanged() // Update ListView
                removeWordFromJson(word)
                Toast.makeText(this, "Word removed", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}