package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.MovieDetailsItemModel
import com.polsl.movielibrary.api.models.MovieListItemModel
import com.polsl.movielibrary.api.models.UserMovieDetailsItemModel
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.api.services.MoviesService
import com.polsl.movielibrary.recource.Resource

class MoviesRepositoryImpl(private val moviesService: MoviesService) : MoviesRepository {

    override suspend fun getAllMovies(): Resource<List<MovieListItemModel>> {
        val response = moviesService.getAllMovies()

        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it.movieListItems)
            } ?: Resource.Success(emptyList())
        } else {
            Resource
                    .Failure(errorMessage = "Error occurred while passing list from API to Repository")
        }

    }

    override suspend fun getUserMovies(): Resource<List<UserMovieListItemModel>> {
        val response = moviesService.getUserMovies()

        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it.userMoviesListItems)
            } ?: Resource.Success(emptyList())
        } else {
            Resource
                    .Failure(errorMessage = "Error occurred while passing list from API to Repository")
        }

    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailsItemModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserMovieDetails(id: Int): Resource<UserMovieDetailsItemModel> {
        TODO("Not yet implemented")
    }
}
