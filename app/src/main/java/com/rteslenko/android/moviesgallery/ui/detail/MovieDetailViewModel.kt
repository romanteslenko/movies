package com.rteslenko.android.moviesgallery.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.domain.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _isProgress = MutableLiveData(false)
    val isProgress: LiveData<Boolean> = _isProgress

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            _isProgress.value = true
            getMovieDetailsUseCase(id).let { result ->
                _isProgress.value = false
                when (result) {
                    is Result.LocalSuccess -> {
                        _movie.value = result.data
                        _isError.value = false
                    }
                    is Result.NetworkSuccess -> {
                        _movie.value = result.data
                        _isError.value = false
                    }
                    else -> {
                        _isError.value = true
                    }
                }
            }
        }
    }
}