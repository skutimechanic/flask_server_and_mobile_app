package com.polsl.movielibrary.di.modules

import android.content.Context
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.brokers.RepositoryInvokerImpl
import com.polsl.movielibrary.utils.NetworkConnectionManager
import com.polsl.movielibrary.utils.NetworkConnectionManagerImpl
import com.polsl.movielibrary.utils.UserSession
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val USER_SESSION_PREFERENCES_KEY = "UserSession"

val appModule = module {

    single<RepositoryInvoker> { RepositoryInvokerImpl(get()) }

    single<NetworkConnectionManager> { NetworkConnectionManagerImpl(androidContext()) }

    single {
        UserSession(
                androidContext().getSharedPreferences(
                        USER_SESSION_PREFERENCES_KEY, Context.MODE_PRIVATE
                )
        )
    }

}
