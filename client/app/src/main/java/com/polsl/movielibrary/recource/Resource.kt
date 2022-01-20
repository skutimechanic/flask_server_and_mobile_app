package com.polsl.movielibrary.recource

sealed class Resource<T> {

    data class Success<T>(val value: T) : Resource<T>()

    data class Failure<T>(val errorMessage: String) : Resource<T>()

    data class NoInternetConnection<T>(val message: String) : Resource<T>()

}
