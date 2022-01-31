package com.polsl.movielibrary.ui.userMovies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.databinding.ListItemMyMovieBinding

class UserMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieBinding: ListItemMyMovieBinding? = null

    fun onBind(
        modelListItem: UserMovieListItemModel,
        clickDeleteListener: (Int) -> Unit
    ) {
        movieBinding?.let {
            it.movie = modelListItem
            it.delete.setOnClickListener {
                clickDeleteListener(modelListItem.movie.id)
            }
        }
    }
}