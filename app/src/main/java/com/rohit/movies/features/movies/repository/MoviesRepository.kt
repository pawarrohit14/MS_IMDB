package com.rohit.movies.features.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rohit.movies.db.AppExecutors
import com.rohit.movies.db.CACHE_TIMEOUT
import com.rohit.movies.db.RefreshRateLimiter
import com.rohit.movies.db.dao.MovieDao
import com.rohit.movies.features.movies.model.Movie
import com.rohit.movies.features.movies.model.MoviesResponse
import com.rohit.movies.network.NetworkBoundResource
import com.rohit.movies.network.RestApiService
import com.rohit.movies.network.model.ApiResponse
import com.rohit.movies.network.model.DataWrapper
import java.util.concurrent.TimeUnit

class MoviesRepository
constructor(private val apiService: RestApiService,
            private val appExecutors: AppExecutors,
            private val movieDao: MovieDao) {

    private val mutableSearchMovieLiveData = MutableLiveData<DataWrapper<List<Movie>>>()
    private val refreshRateLimiter: RefreshRateLimiter<String> = RefreshRateLimiter(TimeUnit.MINUTES, CACHE_TIMEOUT.toLong())

    fun getMovies(category: String): LiveData<DataWrapper<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MoviesResponse>(appExecutors) {

            override fun saveCallResult(item: MoviesResponse) {
                addFilterToEveryMovie(item.movieList!!, category)
                movieDao.insertAll(item.movieList!!)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty() || refreshRateLimiter.shouldFetch(category)
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.getAllMovies(category)
            }

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> {
                return apiService.getMovies(category)
            }
        }.asLiveData
    }

    private fun addFilterToEveryMovie(movieList: List<Movie>, category: String) {
        for (movie in movieList) {
            movie.category = category
        }
    }
}
