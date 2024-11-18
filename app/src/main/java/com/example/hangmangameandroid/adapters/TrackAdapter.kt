package com.example.hangmangameandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter
import com.example.hangmangameandroid.R
import com.example.hangmangameandroid.model.Track

class TrackAdapter(context: Context, private val tracks: Array<Track>) :
    ArrayAdapter<Track>(context, R.layout.spinner_item, tracks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_dropdown_item)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinner_item_text)
        textView.text = tracks[position].name
        return view
    }
}