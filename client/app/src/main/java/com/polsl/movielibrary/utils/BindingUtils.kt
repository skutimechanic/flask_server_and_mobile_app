package com.polsl.movielibrary.utils

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider
import com.polsl.movielibrary.R
import com.polsl.movielibrary.repositories.models.ExtendedMovieDetailsItemModel

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

@BindingAdapter("app:setDetailsButtonLabel")
fun setDetailsButtonLabel(button: Button, isUserMovie: Boolean?) {
    button.text = if (isUserMovie == true) button.context.getString(R.string.update_rate) else button.context.getString(R.string.add_movie_button_label)
}

object BindingAdapters {
    @BindingAdapter("isVisible")
    @JvmStatic
    fun View.setVisible(show: Boolean?) {
        visibility = if (show == true) View.VISIBLE else View.GONE
    }
}

