package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class UserInfoOutputModel(
        @SerializedName("success") val success: String,
        @SerializedName("token") val user: LoggedUserInfoModel,
)
