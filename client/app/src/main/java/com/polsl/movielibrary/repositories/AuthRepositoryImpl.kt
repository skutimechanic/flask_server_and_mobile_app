package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.LoggedUserInfoApiModel
import com.polsl.movielibrary.api.models.LoginInputApiModel
import com.polsl.movielibrary.api.services.AuthService
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.utils.UserSession
import com.polsl.movielibrary.utils.isActive

class AuthRepositoryImpl(private val authService: AuthService, private val userSession: UserSession) : AuthRepository {

    override suspend fun login(login: String, password: String): Resource<Boolean> {
        val response = authService.login(LoginInputApiModel(username = login, password = password))

        return when {
            response.isSuccessful -> {
                response.body()?.let { userSession.saveToken(it.token) }
                Resource.Success(true)
            }
            response.code() == 401 -> {
                Resource.Success(false)
            }
            else -> {
                Resource
                        .Failure(errorMessage = "Error")
            }
        }
    }

    override suspend fun logout() {
        userSession.deleteToken()
    }

    override suspend fun getCurrentUserInfo(): Resource<LoggedUserInfoApiModel> {
        val token = userSession.getToken()
        if (token != null && token.isActive()) {
            val response = authService.getLoggedUserInfo(token = "Bearer $token")
            return if (response.isSuccessful) {
                response.body().let {
                    if (it != null)
                        Resource.Success(it.user)
                    else
                        Resource.Failure(errorMessage = "Error retrieving user info")
                }
            } else {
                Resource.Failure(errorMessage = "Error retrieving user info")
            }
        }
        return Resource.Failure(errorMessage = "No user logged in")
    }
}
