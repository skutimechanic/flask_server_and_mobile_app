package com.polsl.movielibrary

import android.app.Application
import com.polsl.movielibrary.di.modules.appModule
import com.polsl.movielibrary.di.modules.networkModule
import com.polsl.movielibrary.di.modules.repositoryModule
import com.polsl.movielibrary.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                )
            )
        }
    }
}