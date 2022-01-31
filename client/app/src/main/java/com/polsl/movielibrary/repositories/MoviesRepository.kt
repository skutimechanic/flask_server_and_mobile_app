package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.MovieListItemModel
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.models.ExtendedMovieDetailsItemModel

interface MoviesRepository {

    suspend fun getAllMovies(): Resource<List<MovieListItemModel>>
    suspend fun getUserMovies(): Resource<List<UserMovieListItemModel>>
    suspend fun getMovieDetails(id: Int): Resource<ExtendedMovieDetailsItemModel?>
    suspend fun addMovieToUserList(id: Int): Resource<ExtendedMovieDetailsItemModel?>
    suspend fun updateUserRate(id: Int, rate: Float): Resource<ExtendedMovieDetailsItemModel?>
}
