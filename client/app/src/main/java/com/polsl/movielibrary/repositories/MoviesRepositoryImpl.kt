package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.MovieListItemModel
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
}
