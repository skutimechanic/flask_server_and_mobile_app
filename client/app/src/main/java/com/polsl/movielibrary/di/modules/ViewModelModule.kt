package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.ui.dashboard.DashboardViewModel
import com.polsl.movielibrary.ui.home.HomeViewModel
import com.polsl.movielibrary.ui.login.LoginViewModel
import com.polsl.movielibrary.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { DashboardViewModel(get(), get(), get(), get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { SettingsViewModel(get()) }
}
