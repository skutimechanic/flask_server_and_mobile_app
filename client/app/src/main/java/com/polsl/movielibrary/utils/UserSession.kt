package com.polsl.movielibrary.utils

import android.content.SharedPreferences

class UserSession constructor(var sharedPreferences: SharedPreferences) {
    companion object {
        const val USER_TOKEN = "userToken"
    }

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getToken(): String? = sharedPreferences.getString(USER_TOKEN, null)

    fun saveToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.commit()
    }

    fun deleteToken() {
        editor.putString(USER_TOKEN, null)
        editor.commit()
    }
}
