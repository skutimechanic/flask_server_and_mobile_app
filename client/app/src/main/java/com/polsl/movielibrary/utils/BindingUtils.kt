package com.polsl.movielibrary.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setImageBackground")
fun setImageBackground(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty()) {
        view.setImageFromUrl(url)
    }
}