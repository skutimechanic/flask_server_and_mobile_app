package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserMovieDetailsOutputModel(
        @SerializedName("data") val movie: UserMovieDetailsItemModel,
        @SerializedName("success") val success: Boolean,
)
