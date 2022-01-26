package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsOutputModel(
        @SerializedName("data") val movie: MovieDetailsItemModel,
        @SerializedName("success") val success: Boolean,
)
