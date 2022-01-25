package com.polsl.movielibrary.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.AuthRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
        private val repository: AuthRepository,
        private val repositoryInvoker: RepositoryInvoker,
) : BaseViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login(login: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()
                val result = repositoryInvoker.flowData { repository.login(login, password) }

                if (result is Resource.Success) {
                    _isLoggedIn.postValue(result.value!!)
                } else {
                    handleError(result)
                }
                hideLoader()
            }
        }
    }
}
