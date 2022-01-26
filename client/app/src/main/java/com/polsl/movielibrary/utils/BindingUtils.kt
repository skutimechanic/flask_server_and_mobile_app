package com.polsl.movielibrary.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setImageBackground")
fun setImageBackground(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty()) {
        view.setImageFromUrl(url)
    }
}

@BindingAdapter("app:setMovieRating")
fun setMovieRating(view: TextView, ratingSum: Int) {
    val rating: Float = ratingSum / 10.0f
    view.text = rating.toString()
}

@BindingAdapter("app:setNumberString")
fun setNumberString(view: TextView, year: Any) {
    view.text = year.toString()
}

