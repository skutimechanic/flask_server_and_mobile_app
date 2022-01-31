package com.polsl.movielibrary.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.repositories.models.ExtendedMovieDetailsItemModel
import com.polsl.movielibrary.ui.base.BaseViewModel
import com.polsl.movielibrary.utils.UserSession
import com.polsl.movielibrary.utils.isActive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
        private val repository: MoviesRepository,
        private val repositoryInvoker: RepositoryInvoker,
        private val userSession: UserSession,
) : BaseViewModel() {

    private val _movieDetails = MutableLiveData<ExtendedMovieDetailsItemModel>()
    val movieDetails: LiveData<ExtendedMovieDetailsItemModel> = _movieDetails
    private val _isUerActive = MutableLiveData<Boolean>()
    val isUserActive: LiveData<Boolean> = _isUerActive

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val token = userSession.getToken()
                if (token != null && token.isActive()) {
                    _isUerActive.postValue(true)
                } else {
                    _isUerActive.postValue(false)
                }
                val result = repositoryInvoker.flowData { repository.getMovieDetails(movieId) }
                if (result is Resource.Success) {
                    _movieDetails.postValue(result.value!!)
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
                    _movieDetails.postValue(result.value!!)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }

    fun updateUserRate(movieId: Int, rate: Float) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.updateUserRate(movieId, rate) }
                if (result is Resource.Success) {
                    _movieDetails.postValue(result.value!!)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.deleteMovie(id) }

                if (result is Resource.Success) {
                    //TODO toast
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }
}