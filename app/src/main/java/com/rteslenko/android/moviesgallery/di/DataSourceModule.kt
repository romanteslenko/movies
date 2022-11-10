package com.rteslenko.android.moviesgallery.di

import com.rteslenko.android.moviesgallery.data.source.DataSource
import com.rteslenko.android.moviesgallery.data.source.local.database.MoviesDatabase
import com.rteslenko.android.moviesgallery.data.source.remote.RemoteDataSource
import com.rteslenko.android.moviesgallery.data.source.local.LocalDataSource
import com.rteslenko.android.moviesgallery.network.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Provides
    @Singleton
    @RemoteDataSource
    fun remoteDataSource(moviesApi: MoviesApi): DataSource = RemoteDataSource(moviesApi)

    @Provides
    @Singleton
    @LocalDataSource
    fun localDataSource(database: MoviesDatabase): DataSource = LocalDataSource(database.moviesDao())
}