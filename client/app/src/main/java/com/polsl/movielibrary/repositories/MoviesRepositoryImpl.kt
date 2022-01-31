package com.polsl.movielibrary.repositories

import com.polsl.movielibrary.api.models.*
import com.polsl.movielibrary.api.services.MoviesService
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.models.ExtendedMovieDetailsItemModel
import retrofit2.Response

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

    override suspend fun getMovieDetails(id: Int): Resource<ExtendedMovieDetailsItemModel?> {
        val response = moviesService.getUserMovieDetails(id)

        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(createModel(it))
            } ?: Resource.Success(null)
        } else {
            handleErrorCallWithToken(id, response)
        }
    }

    override suspend fun addMovieToUserList(id: Int): Resource<ExtendedMovieDetailsItemModel?> {
        val response = moviesService.addMovieToUserList(AddMovieModel(movieId = id))

        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(createModel(it))
            } ?: Resource.Success(null)
        } else {
            Resource
                    .Failure(errorMessage = "Error occurred while passing list from API to Repository")
        }
    }

    override suspend fun updateUserRate(id: Int, rate: Float): Resource<ExtendedMovieDetailsItemModel?> {
        val response = moviesService.updateUserRate(RateMovieModel(movieId = id, rate = (rate * 10).toInt()))

        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(createModel(it))
            } ?: Resource.Success(null)
        } else {
            Resource
                    .Failure(errorMessage = "Error occurred while passing list from API to Repository")
        }
    }

    private suspend fun handleErrorCallWithToken(
            movieId: Int,
            response: Response<UserMovieDetailsOutputModel>
    ): Resource<ExtendedMovieDetailsItemModel?> =
            when {
                response.code() == 404 || response.code() == 401 -> {
                    val secondResponse = moviesService.getMovieDetails(movieId)
                    if (secondResponse.isSuccessful) {
                        secondResponse.body()?.let {
                            Resource.Success(createModel(it))
                        } ?: Resource.Success(null)
                    } else {
                        Resource
                                .Failure(errorMessage = "Error occurred while passing list from API to Repository")
                    }
                }
                else -> {
                    Resource
                            .Failure(errorMessage = "Error occurred while passing list from API to Repository")
                }
            }

    private fun createModel(item: UserMovieDetailsOutputModel) =
            ExtendedMovieDetailsItemModel(
                    movie = item.movie.movie,
                    rate = item.movie.rate,
                    isUserMovie = true
            )

    private fun createModel(item: MovieDetailsOutputModel) =
            ExtendedMovieDetailsItemModel(
                    movie = item.movie,
                    rate = null,
                    isUserMovie = false
            )
}
