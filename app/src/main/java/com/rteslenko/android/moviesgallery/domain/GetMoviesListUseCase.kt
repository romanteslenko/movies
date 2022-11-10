package com.rteslenko.android.moviesgallery.domain

import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.data.repo.MoviesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMoviesListUseCase @Inject constructor(private val repo: MoviesRepository) {

    suspend operator fun invoke(): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            repo.getMoviesList()
        }
    }
}