package com.rteslenko.android.moviesgallery.di

import com.rteslenko.android.moviesgallery.data.repo.MoviesRepository
import com.rteslenko.android.moviesgallery.data.repo.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository
}