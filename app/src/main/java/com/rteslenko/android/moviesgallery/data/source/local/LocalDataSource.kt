package com.rteslenko.android.moviesgallery.data.source.local

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.data.source.DataSource
import com.rteslenko.android.moviesgallery.data.source.local.database.MoviesDao
import com.rteslenko.android.moviesgallery.data.source.local.database.asDbEntities
import com.rteslenko.android.moviesgallery.data.source.local.database.asDomainModel

class LocalDataSource(private val moviesDao: MoviesDao) : DataSource {

    override suspend fun getMoviesList(): Result<List<Movie>> {
        return try {
            val movies = moviesDao.getMovies().asDomainModel()
            Result.LocalSuccess(movies)
        } catch (e: Exception) {
            Result.Failure
        }
    }

    override suspend fun cache(movies: List<Movie>) {
        moviesDao.deleteMovies()
        moviesDao.insertMovies(movies.asDbEntities())
    }

    override suspend fun getSingleMovie(id: Long): Result<Movie> {
        return try {
            val movie = moviesDao.getMovieDetails(id)
                ?: throw IllegalStateException("Movie not found in local db")
            Result.LocalSuccess(movie.asDomainModel())
        } catch (e: Exception) {
            Result.Failure
        }
    }
}