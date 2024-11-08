package com.example.hangmangameandroid.model

import android.graphics.drawable.Drawable
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.hangmangameandroid.R

class ImagesContainer(private val context: Context) {
    private val hangmanImages: MutableMap<Int, Drawable> = mutableMapOf()

    init {
        loadImages()
    }

    private fun loadImages() {
        // Load images using resource IDs from the drawable directory
        hangmanImages[1] = ContextCompat.getDrawable(context, R.drawable.image1)!!
        hangmanImages[2] = ContextCompat.getDrawable(context, R.drawable.image2)!!
        hangmanImages[3] = ContextCompat.getDrawable(context, R.drawable.image3)!!
        hangmanImages[4] = ContextCompat.getDrawable(context, R.drawable.image4)!!
        hangmanImages[5] = ContextCompat.getDrawable(context, R.drawable.image5)!!
        hangmanImages[6] = ContextCompat.getDrawable(context, R.drawable.image6)!!
        hangmanImages[7] = ContextCompat.getDrawable(context, R.drawable.image7)!!
        hangmanImages[8] = ContextCompat.getDrawable(context, R.drawable.image8)!!
        hangmanImages[9] = ContextCompat.getDrawable(context, R.drawable.image9)!!
    }
    // Function to get the image by index
    fun getImage(index: Int): Drawable? {
        return hangmanImages[index]
    }
}
