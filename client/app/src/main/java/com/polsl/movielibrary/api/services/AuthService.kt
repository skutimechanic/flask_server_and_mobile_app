package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.LoginInputApiModel
import com.polsl.movielibrary.api.models.LoginOutputApiModel
import com.polsl.movielibrary.api.models.UserInfoOutputApiModel
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body loginCredentials: LoginInputApiModel): Response<LoginOutputApiModel>

    @GET("auth/me")
    suspend fun getLoggedUserInfo(@Header("Authorization") token: String): Response<UserInfoOutputApiModel>
}
