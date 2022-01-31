package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.BuildConfig
import com.polsl.movielibrary.api.services.AuthService
import com.polsl.movielibrary.api.services.MoviesService
import com.polsl.movielibrary.utils.UserSession
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { provideRetrofit(get()) }
    single<MoviesService> { provideService(get()) }
    single<AuthService> { provideService(get()) }
}

private fun provideRetrofit(userSession: UserSession): Retrofit {
    return Retrofit.Builder()
        .client(provideClient(userSession))
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideClient(userSession: UserSession) =
    OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${userSession.getToken()}")
            .addHeader("Connection", "close")
            .addHeader("Cache-Control", "no-cache")
            .build()
        chain.proceed(newRequest)
    }.build()


private inline fun <reified T> provideService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)
