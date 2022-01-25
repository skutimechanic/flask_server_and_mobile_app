package com.polsl.movielibrary.brokers

import com.polsl.movielibrary.recource.Resource

interface RepositoryInvoker {
    suspend fun <T>flowData(action: suspend () -> Resource<T>): Resource<T>
}
