package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class LoggedUserInfoModel(
        @SerializedName("creation_date") val creation_date: String,
        @SerializedName("email") val email: String,
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String
)
