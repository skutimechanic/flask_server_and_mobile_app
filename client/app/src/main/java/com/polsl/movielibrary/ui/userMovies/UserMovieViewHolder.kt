package com.polsl.movielibrary.ui.userMovies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.databinding.ListItemMyMovieBinding

class UserMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieBinding: ListItemMyMovieBinding? = null

    fun onBind(modelListItem: UserMovieListItemModel) {
        movieBinding?.let {
            it.movie = modelListItem
        }
    }
}