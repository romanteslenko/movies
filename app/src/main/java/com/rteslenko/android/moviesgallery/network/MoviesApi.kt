package com.rteslenko.android.moviesgallery.network

import com.rteslenko.android.moviesgallery.BuildConfig
import com.rteslenko.android.moviesgallery.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path

data class MoviesApiResponse(
    val page: Long,
    val results: List<Movie>
)

interface MoviesApi {

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=popularity.desc")
    suspend fun getMoviesList(): MoviesApiResponse

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getSingleMovie(@Path("id") id: Long): Movie
}