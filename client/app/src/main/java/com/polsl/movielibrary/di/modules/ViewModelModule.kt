package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.ui.userMovies.UserMoviesViewModel
import com.polsl.movielibrary.ui.allMovies.AllMoviesViewModel
import com.polsl.movielibrary.ui.login.LoginViewModel
import com.polsl.movielibrary.ui.movieDetails.MovieDetailsViewModel
import com.polsl.movielibrary.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { UserMoviesViewModel(get(), get(), get(), get()) }

    viewModel { AllMoviesViewModel(get(), get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { MovieDetailsViewModel(get(), get(), get()) }
}
