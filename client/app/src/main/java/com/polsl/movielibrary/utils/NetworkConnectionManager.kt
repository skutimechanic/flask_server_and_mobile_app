package com.polsl.movielibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectionManagerImpl(private val context: Context) : NetworkConnectionManager {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    private val capabilities
        get() = connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)

    override fun isConnectedToWifi(): Boolean =
        capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

    override fun isConnectedToNetwork(): Boolean =
        capabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false
}

interface NetworkConnectionManager {
    fun isConnectedToWifi(): Boolean
    fun isConnectedToNetwork(): Boolean
}
