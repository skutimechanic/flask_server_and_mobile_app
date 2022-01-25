package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.MovieApiModel
import com.polsl.movielibrary.recource.Resource

interface MoviesRepository {

    suspend fun getAllMovies(): Resource<List<MovieApiModel>>
}
