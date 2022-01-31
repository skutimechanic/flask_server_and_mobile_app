package com.polsl.movielibrary.ui.userMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.AuthRepository
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import com.polsl.movielibrary.utils.SingleLiveEvent
import com.polsl.movielibrary.utils.UserSession
import com.polsl.movielibrary.utils.isActive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserMoviesViewModel(
        private val repository: MoviesRepository,
        private val authRepository: AuthRepository,
        private val repositoryInvoker: RepositoryInvoker,
        private val userSession: UserSession,
) : BaseViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _userInfo = MutableLiveData<String>()

    val userInfo: LiveData<String> = _userInfo

    private val _movies = MutableLiveData<List<UserMovieListItemModel>>()

    val movies: LiveData<List<UserMovieListItemModel>> = _movies

    private val _removedMovie = SingleLiveEvent(false)
    val removedMovie: LiveData<Boolean> = _removedMovie

    fun isUserLoggedIn() {
        val token = userSession.getToken()
        _isLoggedIn.postValue(token != null && token.isActive())
    }

    fun getUserInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { authRepository.getCurrentUserInfo() }

                if (result is Resource.Success) {
                    _userInfo.postValue(result.value.username)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }

    fun loadUSerMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.getUserMovies() }

                if (result is Resource.Success) {
                    _movies.postValue(result.value!!)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }

    fun deleteMovieFromUserList(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.deleteMovieFromUserList(id) }

                if (result is Resource.Success) {
                    _removedMovie.postValue(true)
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }
}
