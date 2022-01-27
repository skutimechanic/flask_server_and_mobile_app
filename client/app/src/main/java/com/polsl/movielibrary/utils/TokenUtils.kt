package com.polsl.movielibrary.utils

import com.auth0.android.jwt.JWT

fun String.isActive(): Boolean {
    val jwt = JWT(this)
    return !jwt.isExpired(0)
}

fun String.isAdmin(): Boolean = JWT(this).getClaim("user_is_admin").asBoolean() ?: false
