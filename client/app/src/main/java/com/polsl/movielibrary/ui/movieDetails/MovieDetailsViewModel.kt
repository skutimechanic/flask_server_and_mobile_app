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

    fun loadDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val token = userSession.getToken()
                val result = if (token != null && token.isActive()) {
                    _isUerActive.postValue(true)
                    repositoryInvoker.flowData { repository.getUserMovieDetails(id) }
                } else {
                    _isUerActive.postValue(false)
                    repositoryInvoker.flowData { repository.getMovieDetails(id) }
                }

                if (result is Resource.Success) {
                    _movieDetails.postValue(result.value!!)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }
}