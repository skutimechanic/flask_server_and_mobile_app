package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class AddMovieModel(
        @SerializedName("movie_id") val movieId: Int,
)
