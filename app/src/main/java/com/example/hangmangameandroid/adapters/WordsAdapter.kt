package com.example.hangmangameandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.hangmangameandroid.R

class WordsAdapter(private val context: Context, private val wordList: MutableList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return wordList.size
    }

    override fun getItem(position: Int): Any {
        return wordList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.words_list_view, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val word = getItem(position) as String
        viewHolder.wordItem.text = word

        return view
    }

    fun getWords(): MutableList<String> {
        return wordList
    }

    private class ViewHolder(view: View) {
        val wordItem: TextView = view.findViewById(R.id.wordItem)
    }
}