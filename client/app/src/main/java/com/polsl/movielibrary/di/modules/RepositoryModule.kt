package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.repositories.MoviesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }
}