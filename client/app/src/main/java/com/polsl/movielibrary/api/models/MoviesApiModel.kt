package com.polsl.movielibrary.api.models

import com.google.gson.annotations.SerializedName

data class MoviesApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_link") val image_link: String,
    @SerializedName("rating_sum") val rating_sum: Int,
    @SerializedName("number_of_votes") val number_of_votes: Int,
    @SerializedName("category") val category: String,
    @SerializedName("director") val director: String,
    @SerializedName("year") val year: Int,
    @SerializedName("country") val country: String,
)
