package com.rohit.movies.db.di

import androidx.room.Room
import com.rohit.movies.db.AppExecutors
import com.rohit.movies.db.DATA_BASE_NAME
import com.rohit.movies.db.MovieDatabase
import org.koin.dsl.module

val dataBaseModule = module {
    single { Room.databaseBuilder(get(), MovieDatabase::class.java, DATA_BASE_NAME).build()}
    single { get<MovieDatabase>().movieDao }
    single { AppExecutors() }
}