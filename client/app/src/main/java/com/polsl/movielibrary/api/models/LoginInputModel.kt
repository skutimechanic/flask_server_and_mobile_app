package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class LoginInputModel(
        @SerializedName("username") val username: String,
        @SerializedName("password") val password: String,
)
