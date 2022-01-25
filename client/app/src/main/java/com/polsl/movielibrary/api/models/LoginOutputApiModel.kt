package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class LoginOutputApiModel(
        @SerializedName("success") val success: Boolean,
        @SerializedName("token") val token: String,
)
