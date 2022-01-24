package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class MoviesOutputApiModel(
        @SerializedName("data") val movies: List<MovieApiModel>,
        @SerializedName("number_of_records") val number_of_records: Int,
        @SerializedName("success") val success: Boolean,
)