package com.polsl.movielibrary.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider

@BindingAdapter("app:setImageBackground")
fun setImageBackground(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty()) {
        view.setImageFromUrl(url)
    }
}

@BindingAdapter("app:setMovieRating")
fun setMovieRating(view: TextView, ratingSum: Int?) {
    if (ratingSum == null) {
        view.text = "--"
    } else {
        val rating: Float = ratingSum / 10.0f
        view.text = rating.toString()
    }
}

@BindingAdapter("app:setMovieRatingWithRange")
fun setMovieRatingWithRange(view: TextView, ratingSum: Int?) {
    if (ratingSum == null) {
        view.text = "--"
    } else {
        val rating: Float = ratingSum / 10.0f
        view.text = "$rating/10.0"
    }
}

@BindingAdapter("app:setNumberString")
fun setNumberString(view: TextView, year: Any) {
    view.text = year.toString()
}

@BindingAdapter("app:setMovieRatingSlider")
fun setMovieRatingSlider(view: Slider, ratingSum: Int?) {
    when {
        ratingSum == null || ratingSum < 0.0f -> {
            view.value = 0.0f
        }
        ratingSum > 100.0f -> {
            view.value = 10.0f
        }
        else -> {
            val rating: Float = ratingSum / 10.0f
            view.value = rating
        }
    }
}

