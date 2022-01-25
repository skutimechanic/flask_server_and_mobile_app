package com.polsl.movielibrary.ui.settings

import com.polsl.movielibrary.ui.base.BaseViewModel
import com.polsl.movielibrary.utils.UserSession

class SettingsViewModel(private val userSession: UserSession) : BaseViewModel() {

    fun logout() {
        userSession.deleteToken()
    }
}