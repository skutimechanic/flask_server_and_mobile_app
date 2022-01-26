package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserMoviesOutputModel(
        @SerializedName("data") val userMoviesListItems: List<UserMovieListItemModel>,
        @SerializedName("success") val success: Boolean,
)
