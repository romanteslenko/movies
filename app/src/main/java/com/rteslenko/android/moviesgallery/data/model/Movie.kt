package com.rteslenko.android.moviesgallery.data.model

import com.squareup.moshi.Json

data class Movie(
    val id: Long,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val title: String
)
