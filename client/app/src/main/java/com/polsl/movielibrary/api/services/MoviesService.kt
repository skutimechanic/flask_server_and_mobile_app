package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.*
import retrofit2.Response
import retrofit2.http.*

interface MoviesService {

    @GET("movies")
    suspend fun getAllMovies(): Response<AllMoviesOutputModel>

    @GET("movies/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDetailsOutputModel>

    @GET("user/movies")
    suspend fun getUserMovies(): Response<UserMoviesOutputModel>

    @GET("user/movies/{id}")
    suspend fun getUserMovieDetails(@Path("id") id: Int): Response<UserMovieDetailsOutputModel>

    @POST("user/movies")
    suspend fun addMovieToUserList(@Body addMovieModel: AddMovieModel): Response<UserMovieDetailsOutputModel>

    @PUT("user/movies")
    suspend fun updateUserRate(@Body rateMovieModel: RateMovieModel): Response<UserMovieDetailsOutputModel>
}
