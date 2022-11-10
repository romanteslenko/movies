package com.rteslenko.android.moviesgallery.data.source.remote

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.data.source.DataSource
import com.rteslenko.android.moviesgallery.network.MoviesApi

class RemoteDataSource(private val moviesApi: MoviesApi) : DataSource {

    override suspend fun getMoviesList(): Result<List<Movie>> {
        return try {
            val movies = moviesApi.getMoviesList().results
            Result.NetworkSuccess(movies)
        } catch (e: Exception) {
            Result.Failure
        }
    }

    override suspend fun cache(movies: List<Movie>) {}

    override suspend fun getSingleMovie(id: Long): Result<Movie> {
        return try {
            val movie = moviesApi.getSingleMovie(id)
            Result.NetworkSuccess(movie)
        } catch (e: Exception) {
            Result.Failure
        }
    }
}