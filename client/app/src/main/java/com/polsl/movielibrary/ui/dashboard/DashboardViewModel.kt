package com.polsl.movielibrary.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.AuthRepository
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import com.polsl.movielibrary.utils.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(
        private val repository: MoviesRepository,
        private val authRepository: AuthRepository,
        private val repositoryInvoker: RepositoryInvoker,
        private val userSession: UserSession,
) : BaseViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _userInfo = MutableLiveData<String>()

    val userInfo: LiveData<String> = _userInfo

    fun loadMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()

                val result = repositoryInvoker.flowData { repository.getAllMovies() }

                if (result is Resource.Success) {
                    Log.d("Movies", result.value.toString())
                } else {
                    handleError(result)
                }

                hideLoader()
            }
        }
    }

    fun isUserLoggedIn() {
        val token = userSession.getToken()
        _isLoggedIn.postValue(token != null)
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
}
