package com.rteslenko.android.moviesgallery.data.repo

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import javax.inject.Singleton

@Singleton
interface MoviesRepository {

    suspend fun getMoviesList(): Result<List<Movie>>

    suspend fun getSingleMovie(id: Long): Result<Movie>
}
