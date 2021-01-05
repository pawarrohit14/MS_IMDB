package com.rohit.movies.features.movies.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoviesResponse {
    @SerializedName("results")
    @Expose
    var movieList: List<Movie>? = null
}
