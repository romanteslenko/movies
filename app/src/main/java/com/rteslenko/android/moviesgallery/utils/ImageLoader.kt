package com.rteslenko.android.moviesgallery.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rteslenko.android.moviesgallery.R

class ImageLoader(private val context: Context) {

    fun loadPreview(path: String, target: ImageView) {
        val url = BASE_IMAGE_URL + PREVIEW_SIZE + path
        load(url, target)
    }

    fun loadPoster(path: String, target: ImageView) {
        val url = BASE_IMAGE_URL + POSTER_SIZE + path
        load(url, target)
    }

    private fun load(url: String, target: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(target)
    }

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
        private const val PREVIEW_SIZE = "w185"
        private const val POSTER_SIZE = "w500"

    }
}