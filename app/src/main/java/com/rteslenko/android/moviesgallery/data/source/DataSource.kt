package com.rteslenko.android.moviesgallery.data.source

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie

interface DataSource {

    suspend fun getMoviesList(): Result<List<Movie>>

    suspend fun cache(movies: List<Movie>)

    suspend fun getSingleMovie(id: Long): Result<Movie>
}