package com.rohit.movies.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rohit.movies.features.movies.model.Movie
import com.rohit.movies.features.movies.repository.MoviesRepository
import com.rohit.movies.network.model.DataWrapper


class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val category = MutableLiveData<String>()
    val moviesList: LiveData<DataWrapper<List<Movie>>>

    init {
        category.value = "top_rated"
        moviesList = Transformations.switchMap(category) { category -> repository.getMovies(category) }
    }



}
