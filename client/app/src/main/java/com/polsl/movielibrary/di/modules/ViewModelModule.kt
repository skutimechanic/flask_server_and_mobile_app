package com.polsl.movielibrary.di.modules

import com.polsl.movielibrary.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { DashboardViewModel(get(), get()) }

}
