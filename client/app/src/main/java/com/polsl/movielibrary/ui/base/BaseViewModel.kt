package com.polsl.movielibrary.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polsl.movielibrary.recource.Resource

abstract class BaseViewModel : ViewModel() {

    private val _isLoaderVisible = MutableLiveData<Boolean>()
    private val _loadedFailed = MutableLiveData<String>()
    private val _noInternet = MutableLiveData<String>()

    val isLoaderVisible: LiveData<Boolean>
        get() = _isLoaderVisible

    val loadedFailed: LiveData<String>
        get() = _loadedFailed

    val noInternet: LiveData<String>
        get() = _noInternet

    fun showLoader() = _isLoaderVisible.postValue(true)

    fun hideLoader() = _isLoaderVisible.postValue(false)

    fun <T> handleError(result: Resource<T>) {
        when (result) {
            is Resource.Failure -> _loadedFailed.postValue(result.errorMessage)
            is Resource.NoInternetConnection -> _noInternet.postValue(result.message)
        }
    }

}