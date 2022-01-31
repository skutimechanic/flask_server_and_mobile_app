package com.polsl.movielibrary.ui.allMovies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.api.models.MovieListItemModel
import com.polsl.movielibrary.databinding.ListItemMovieBinding

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieBinding: ListItemMovieBinding? = null

    fun onBind(
        modelListItem: MovieListItemModel,
        clickFavoriteListener: (Int) -> Unit
    ) {
        movieBinding?.let {
            it.movie = modelListItem
            it.addToFavorite.setOnClickListener {
                clickFavoriteListener(modelListItem.id)
            }
        }
    }
}
