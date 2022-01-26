package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class MoviesOutputModel(
        @SerializedName("data") val movieListItems: List<MovieListItemModel>,
        @SerializedName("number_of_records") val number_of_records: Int,
        @SerializedName("success") val success: Boolean,
)
