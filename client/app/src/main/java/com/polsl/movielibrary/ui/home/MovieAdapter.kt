package com.polsl.movielibrary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.R
import com.polsl.movielibrary.api.models.MovieApiModel
import com.polsl.movielibrary.databinding.ListItemMovieBinding

class MovieAdapter :
        RecyclerView.Adapter<MovieViewHolder>() {
    private val moviesList = mutableListOf<MovieApiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemMovieBinding = DataBindingUtil
                .inflate(inflater, R.layout.list_item_movie, parent, false)

        return MovieViewHolder(binding.root).apply {
            this.movieBinding = binding
        }
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.apply {
            onBind(moviesList[position])
        }
    }

    fun setItems(items: List<MovieApiModel>) {
        moviesList.clear()
        moviesList.addAll(items)
        notifyDataSetChanged()
    }
}