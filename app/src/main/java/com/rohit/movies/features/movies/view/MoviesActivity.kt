package com.rohit.movies.features.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rohit.movies.R
import com.rohit.movies.base.BaseActivity
import com.rohit.movies.features.movies.model.Movie
import com.rohit.movies.features.movies.view.adapter.ITEM_MOVIE
import com.rohit.movies.features.movies.view.adapter.MoviesAdapter
import com.rohit.movies.features.movies.view.callback.OnMovieClickListener
import com.rohit.movies.features.movies.viewmodel.MoviesViewModel
import com.rohit.movies.features.splashscreen.DetailScreenActivity
import com.rohit.movies.network.model.DataWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading_movies.*
import kotlinx.android.synthetic.main.layout_no_data.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, OnMovieClickListener{


    private val moviesViewModel: MoviesViewModel by viewModel()


    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        // set swipe listener
        swipeRefreshLayout.setOnRefreshListener(this)
        // load movies from network or db source
        getTopMovies()
    }


    private fun getTopMovies() {
        moviesViewModel.moviesList.observe(this, Observer { dataWrapper ->
            when (dataWrapper.status) {
                DataWrapper.Status.LOADING -> showLoadingLayout()
                DataWrapper.Status.SUCCESS -> {
                    hideLoadingLayout()
                    setupMoviesRecycler(dataWrapper.data)
                }
                DataWrapper.Status.ERROR -> {
                    hideLoadingLayout()
                    handleMoviesListError(dataWrapper.message)
                }
            }
        })
    }


    private fun showLoadingLayout() {
        mainFrameLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        noDataLayout.visibility = View.GONE
    }

    private fun hideLoadingLayout() {
        if (swipeRefreshLayout.isRefreshing)
            swipeRefreshLayout.isRefreshing = false
        mainFrameLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }


    private fun setDataViewsVisibility(dataAvailable: Boolean) {
        if (dataAvailable) {
            recyclerView.visibility = View.VISIBLE
            noDataLayout.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            noDataLayout.visibility = View.VISIBLE
        }
    }


    private fun handleMoviesListError(message: String?) {
        setDataViewsVisibility(false)
        noDataTxtView.text = message
    }

    private fun setupMoviesRecycler(moviesList: List<Movie>?) {
        hideLoadingLayout()
        setDataViewsVisibility(true)
        val adapter = MoviesAdapter(moviesList, ITEM_MOVIE, this)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onRefresh() {

    }

    override fun onMovieClick(movie: Movie, view: View) {
        val intent = Intent(this, DetailScreenActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("MOVIE-EXTRA", movie)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_up, R.anim.no_animation)
    }

}
