package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.LoggedUserInfoApiModel
import com.polsl.movielibrary.recource.Resource

interface AuthRepository {
    suspend fun login(login: String, password: String): Resource<Boolean>
    suspend fun logout()
    suspend fun getCurrentUserInfo(): Resource<LoggedUserInfoApiModel>
}
