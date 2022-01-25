package com.polsl.movielibrary.api.services

import com.polsl.movielibrary.api.models.MoviesOutputApiModel
import retrofit2.Response
import retrofit2.http.GET

interface MoviesService {

    @GET("movies")
    suspend fun getAllMovies(): Response<MoviesOutputApiModel>
}
