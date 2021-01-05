package com.rohit.movies.network


import androidx.lifecycle.LiveData

import com.rohit.movies.features.movies.model.MoviesResponse
import com.rohit.movies.network.model.ApiResponse

import retrofit2.http.GET
import retrofit2.http.Path

interface RestApiService {

    @GET("movie/{category}")
    fun getMovies(@Path("category") category: String): LiveData<ApiResponse<MoviesResponse>>
}
