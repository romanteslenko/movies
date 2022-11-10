package com.rteslenko.android.moviesgallery.data

sealed interface Result<out R> {
    data class NetworkSuccess<out T>(val data: T) : Result<T>
    data class LocalSuccess<out T>(val data: T) : Result<T>
    object Failure : Result<Nothing>
}
