package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserMovieDetailsItemModel(
        @SerializedName("movie") val movie: MovieDetailsItemModel,
        @SerializedName("rate") val rate: Int,
)
