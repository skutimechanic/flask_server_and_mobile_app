package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.MovieDetailsItemModel
import com.polsl.movielibrary.api.models.MovieListItemModel
import com.polsl.movielibrary.api.models.UserMovieDetailsItemModel
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.recource.Resource

interface MoviesRepository {

    suspend fun getAllMovies(): Resource<List<MovieListItemModel>>
    suspend fun getUserMovies(): Resource<List<UserMovieListItemModel>>
    suspend fun getMovieDetails(id: Int): Resource<MovieDetailsItemModel>
    suspend fun getUserMovieDetails(id: Int): Resource<UserMovieDetailsItemModel>
}
