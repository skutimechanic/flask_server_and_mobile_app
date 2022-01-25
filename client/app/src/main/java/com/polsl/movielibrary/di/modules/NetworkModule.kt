package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.BuildConfig
import com.polsl.movielibrary.api.services.AuthService
import com.polsl.movielibrary.api.services.MoviesService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single<MoviesService> { provideService(get()) }
    single<AuthService> { provideService(get()) }
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private inline fun <reified T> provideService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)
