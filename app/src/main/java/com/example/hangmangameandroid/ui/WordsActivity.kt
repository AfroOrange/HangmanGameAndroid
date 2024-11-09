package com.example.hangmangameandroid.ui

import android.os.Bundle
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
import com.example.hangmangameandroid.model.FileUtils

class WordsActivity : AppCompatActivity() {

    private lateinit var addWordButton: Button
    private lateinit var addWordField: EditText
    private lateinit var wordsListView: ListView
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var wordList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        addWordButton = findViewById(R.id.addWordButton)
        addWordField = findViewById(R.id.addWordField)
        wordsListView = findViewById(R.id.wordsList)

        wordList = FileUtils.readWords(this)
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
        val words = FileUtils.readWords(this)
        words.add(word)
        FileUtils.writeWords(this, words)
    }

    // Remove word from JSON file
    private fun removeWordFromJson(word: String) {
        val words = FileUtils.readWords(this)
        words.remove(word)
        FileUtils.writeWords(this, words)
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