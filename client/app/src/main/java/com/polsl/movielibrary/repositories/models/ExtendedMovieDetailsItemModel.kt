package com.polsl.movielibrary.repositories.models

import com.google.gson.annotations.SerializedName
import com.polsl.movielibrary.api.models.MovieDetailsItemModel

data class ExtendedMovieDetailsItemModel(
    @SerializedName("movie") val movie: MovieDetailsItemModel,
    @SerializedName("rate") val rate: Int?,
    val isUserMovie: Boolean = false,
)
