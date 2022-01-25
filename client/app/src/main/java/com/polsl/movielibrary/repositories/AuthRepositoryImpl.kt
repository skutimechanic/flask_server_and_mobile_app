package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.LoginInputApiModel
import com.polsl.movielibrary.api.services.AuthService
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.utils.UserSession

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
}
