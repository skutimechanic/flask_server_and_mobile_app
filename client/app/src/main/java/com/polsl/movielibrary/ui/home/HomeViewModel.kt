package com.polsl.movielibrary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.api.models.MovieApiModel
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
        private val repository: MoviesRepository,
        private val repositoryInvoker: RepositoryInvoker,
) : BaseViewModel() {

    private val _movies = MutableLiveData<List<MovieApiModel>>()

    val movies: LiveData<List<MovieApiModel>> = _movies

    fun loadMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.getAllMovies() }

                if (result is Resource.Success) {
                    _movies.postValue(result.value!!)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }
}
