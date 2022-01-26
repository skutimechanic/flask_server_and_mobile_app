package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.LoginInputModel
import com.polsl.movielibrary.api.models.LoginOutputModel
import com.polsl.movielibrary.api.models.UserInfoOutputModel
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body loginCredentials: LoginInputModel): Response<LoginOutputModel>

    @GET("auth/me")
    suspend fun getLoggedUserInfo(@Header("Authorization") token: String): Response<UserInfoOutputModel>
}
