package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserInfoOutputApiModel(
        @SerializedName("success") val success: String,
        @SerializedName("token") val user: LoggedUserInfoApiModel,
)
