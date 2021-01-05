package com.rohit.movies.features.movies.di

import com.rohit.movies.features.movies.repository.MoviesRepository
import com.rohit.movies.features.movies.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    factory { MoviesRepository(get(), get(), get()) }
    viewModel { MoviesViewModel(get()) }
}