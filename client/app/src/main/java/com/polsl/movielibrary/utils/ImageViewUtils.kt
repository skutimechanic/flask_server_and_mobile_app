package com.polsl.movielibrary.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URL

fun ImageView.setImageFromUrl(imageUrl: String) {
    getBitmapDrawableFromUrl(imageUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bmp ->
                setImageBitmap(bmp)
            }
}


private fun getBitmapDrawableFromUrl(imageUrl: String): Observable<Bitmap?> {
    return Observable.create { subscriber ->
        val url = URL(imageUrl)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        subscriber.onNext(bmp)
    }
}