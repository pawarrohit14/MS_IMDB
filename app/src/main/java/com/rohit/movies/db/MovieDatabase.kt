package com.rohit.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohit.movies.db.dao.MovieDao
import com.rohit.movies.features.movies.model.Movie

const val DATA_BASE_NAME = "movies_db"
const val CACHE_TIMEOUT = 1 // 1 minute

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

}
