package com.polsl.movielibrary.ui.allMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.api.models.MovieListItemModel
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import com.polsl.movielibrary.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMoviesViewModel(
        private val repository: MoviesRepository,
        private val repositoryInvoker: RepositoryInvoker,
) : BaseViewModel() {

    private val _movies = MutableLiveData<List<MovieListItemModel>>()
    val movies: LiveData<List<MovieListItemModel>> = _movies

    private val _addToFavoriteFinished = SingleLiveEvent(false)
    val addToFavoriteFinished: LiveData<Boolean> = _addToFavoriteFinished

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

    fun addMovieToUserList(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.addMovieToUserList(movieId) }
                if (result is Resource.Success) {
                    _addToFavoriteFinished.postValue(true)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }
}
