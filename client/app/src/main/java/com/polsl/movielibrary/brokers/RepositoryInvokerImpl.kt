package com.polsl.movielibrary.brokers

import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.utils.NetworkConnectionManager

internal class RepositoryInvokerImpl(
    private val networkConnectionManager: NetworkConnectionManager
) : RepositoryInvoker {
    override suspend fun <T> flowData(action: suspend () -> Resource<T>): Resource<T> {
        if (!networkConnectionManager.isConnectedToNetwork()) {
            return Resource.NoInternetConnection("No Internet connection!")
        }

        return action()
    }
}
