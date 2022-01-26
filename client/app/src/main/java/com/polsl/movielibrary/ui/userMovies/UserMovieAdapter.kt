package com.polsl.movielibrary.ui.userMovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.R
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.databinding.ListItemMyMovieBinding

class UserMovieAdapter :
        RecyclerView.Adapter<UserMovieViewHolder>() {
    private val moviesList = mutableListOf<UserMovieListItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemMyMovieBinding = DataBindingUtil
                .inflate(inflater, R.layout.list_item_my_movie, parent, false)

        return UserMovieViewHolder(binding.root).apply {
            this.movieBinding = binding
        }
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(viewHolder: UserMovieViewHolder, position: Int) {
        viewHolder.apply {
            onBind(moviesList[position])
        }
    }

    fun setItems(items: List<UserMovieListItemModel>) {
        moviesList.clear()
        moviesList.addAll(items)
        notifyDataSetChanged()
    }
}