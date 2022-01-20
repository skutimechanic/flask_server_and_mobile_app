package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.brokers.RepositoryInvokerImpl
import com.polsl.movielibrary.utils.NetworkConnectionManager
import com.polsl.movielibrary.utils.NetworkConnectionManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<RepositoryInvoker> { RepositoryInvokerImpl(get()) }

    single<NetworkConnectionManager> { NetworkConnectionManagerImpl(androidContext()) }

}