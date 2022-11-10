package com.rteslenko.android.moviesgallery.di

import com.rteslenko.android.moviesgallery.network.MoviesApi
import com.rteslenko.android.moviesgallery.network.UrlProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun urlProvider() = UrlProvider()

    @Provides
    @Singleton
    fun retrofit(moshi: Moshi) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(urlProvider().baseUrl)
        .build()

    @Provides
    @Singleton
    fun moviesApi(retrofit: Retrofit) = retrofit.create(MoviesApi::class.java)
}