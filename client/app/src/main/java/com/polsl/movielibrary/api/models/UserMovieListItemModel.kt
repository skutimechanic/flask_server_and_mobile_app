package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserMovieListItemModel(
        @SerializedName("movie") val movie: MovieListItemModel,
        @SerializedName("rate") val rate: Int?,
)
