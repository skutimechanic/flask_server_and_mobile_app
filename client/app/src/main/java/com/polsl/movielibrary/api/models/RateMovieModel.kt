package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class RateMovieModel(
        @SerializedName("movie_id") val movieId: Int,
        @SerializedName("rate") val rate: Int,
)
