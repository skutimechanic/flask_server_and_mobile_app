package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class DeleteUserMovieOutputModel(
        @SerializedName("data") val message: String,
        @SerializedName("success") val success: Boolean,
)
