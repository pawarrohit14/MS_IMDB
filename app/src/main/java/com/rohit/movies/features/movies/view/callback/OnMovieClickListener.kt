package com.rohit.movies.features.movies.view.callback

import android.view.View

import com.rohit.movies.features.movies.model.Movie

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie, view: View)
}
