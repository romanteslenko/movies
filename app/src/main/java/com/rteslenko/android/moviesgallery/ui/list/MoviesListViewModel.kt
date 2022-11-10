package com.rteslenko.android.moviesgallery.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.rteslenko.android.moviesgallery.data.Result
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.domain.GetMoviesListUseCase
import com.rteslenko.android.moviesgallery.utils.combineWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>> = _movies

    private val _isProgress = MutableLiveData(false)
    val isProgress: LiveData<Boolean> = _isProgress

    private val _isNetworkError = MutableLiveData(false)
    private var isErrorShown = false
    val isNetworkError: LiveData<Boolean> = _isNetworkError

    val isEmpty: LiveData<Boolean> =
        _movies.combineWith(_isProgress) { list: List<Movie>?, progress: Boolean? ->
            progress == false && list?.isEmpty() == true
        }

    init {
        Log.i("Roman", "ViewModel init")
        refresh()
    }

    fun refresh() {
        Log.i("Roman", "ViewModel refresh")
        viewModelScope.launch {
            _isProgress.value = true
            getMoviesListUseCase().let { result ->
                _isProgress.value = false
                when (result) {
                    is Result.NetworkSuccess -> {
                        _movies.value = result.data
                    }
                    is Result.LocalSuccess -> {
                        _movies.value = result.data
                        _isNetworkError.value = !isErrorShown
                    }
                    else -> {
                        _isNetworkError.value = !isErrorShown
                    }
                }
            }
        }
    }

    fun notifyNetworkErrorShown() {
        isErrorShown = true
    }
}