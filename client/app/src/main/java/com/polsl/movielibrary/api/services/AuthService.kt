package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.LoginInputApiModel
import com.polsl.movielibrary.api.models.LoginOutputApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body loginCredentials: LoginInputApiModel): Response<LoginOutputApiModel>
}
