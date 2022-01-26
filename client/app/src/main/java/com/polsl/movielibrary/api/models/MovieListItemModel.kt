package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class MovieListItemModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_link") val image_link: String,
    @SerializedName("rating_sum") val rating_sum: Int,
    @SerializedName("category") val category: String,
    @SerializedName("year") val year: Int,
)
