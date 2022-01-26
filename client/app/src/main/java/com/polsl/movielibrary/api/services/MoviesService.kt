package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.AllMoviesOutputModel
import com.polsl.movielibrary.api.models.MovieDetailsOutputModel
import com.polsl.movielibrary.api.models.UserMovieDetailsOutputModel
import com.polsl.movielibrary.api.models.UserMoviesOutputModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movies")
    suspend fun getAllMovies(): Response<AllMoviesOutputModel>

    @GET("movies/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDetailsOutputModel>

    @GET("user/movies")
    suspend fun getUserMovies(): Response<UserMoviesOutputModel>

    @GET("user/movies/{id}")
    suspend fun getUserMovieDetails(@Path("id") id: Int): Response<UserMovieDetailsOutputModel>
}
