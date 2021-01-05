package com.rohit.movies.features.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import com.rohit.movies.BuildConfig
import com.rohit.movies.R
import com.rohit.movies.base.BaseViewHolder
import com.rohit.movies.features.movies.model.Movie
import com.rohit.movies.features.movies.view.callback.OnMovieClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.movieImageView

const val ITEM_MOVIE = 0

@Retention()
@IntDef( ITEM_MOVIE)
annotation class ItemType

class MoviesAdapter(private val movieList: List<Movie>?, @ItemType private val itemType: Int, private val onMovieClickListener: OnMovieClickListener) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View

            view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MoviesViewHolder(view)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindViews(position)
    }

    override fun getItemCount(): Int {
        return if (movieList != null && !movieList.isEmpty()) {
            movieList.size
        } else {
            0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemType
    }

    private inner class MoviesViewHolder internal constructor(itemView: View) : BaseViewHolder(itemView) {

        override fun bindViews(position: Int) {
            super.bindViews(position)
            val movie = movieList!![position]
            displayArticleImage(movie.posterPath, itemView)
            itemView.setOnClickListener { onMovieClickListener.onMovieClick(movie, itemView) }
        }

        override fun clear() {
            itemView.movieImageView!!.setImageDrawable(null)
        }
    }

    private fun displayArticleImage(imageUrl: String?, itemView: View) {
        if (imageUrl != null) {
            Picasso.get().load(BuildConfig.IMAGE_BASE_URL + imageUrl).error(R.drawable.placeholder).into(itemView.movieImageView)
        } else {
            Picasso.get().load(R.drawable.placeholder).into(itemView.movieImageView)
        }
    }
}
