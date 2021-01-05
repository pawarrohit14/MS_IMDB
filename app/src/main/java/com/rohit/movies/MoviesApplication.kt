package com.rohit.movies

import android.app.Application
import com.rohit.movies.db.di.dataBaseModule
import com.rohit.movies.features.movies.di.moviesModule
import com.rohit.movies.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoinInjection();
    }

    private fun startKoinInjection() {
        startKoin {
            androidContext(this@MoviesApplication)
            modules(listOf(networkModule, dataBaseModule, moviesModule))
        }
    }
}
