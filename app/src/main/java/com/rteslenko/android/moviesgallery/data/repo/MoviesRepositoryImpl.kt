package com.rteslenko.android.moviesgallery.data.repo

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.data.source.DataSource
import com.rteslenko.android.moviesgallery.di.DataSourceModule.LocalDataSource
import com.rteslenko.android.moviesgallery.di.DataSourceModule.RemoteDataSource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: DataSource,
    @LocalDataSource private val localDataSource: DataSource
) : MoviesRepository {

    override suspend fun getMoviesList(): Result<List<Movie>> {
        val remoteResult = remoteDataSource.getMoviesList()
        if (remoteResult is Result.NetworkSuccess<List<Movie>>) {
            localDataSource.cache(remoteResult.data)
            return remoteResult
        } else {
            return localDataSource.getMoviesList()
        }
    }

    override suspend fun getSingleMovie(id: Long): Result<Movie> {
        val localResult = localDataSource.getSingleMovie(id)
        return if (localResult is Result.LocalSuccess<Movie>) {
            localResult
        } else {
            remoteDataSource.getSingleMovie(id)
        }
    }
}