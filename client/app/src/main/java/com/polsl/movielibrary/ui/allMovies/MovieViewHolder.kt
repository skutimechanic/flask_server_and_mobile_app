package com.polsl.movielibrary.ui.allMovies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.api.models.MovieApiModel
import com.polsl.movielibrary.databinding.ListItemMovieBinding

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieBinding: ListItemMovieBinding? = null

    fun onBind(model: MovieApiModel) {
        movieBinding?.let {
            it.movie = model
        }
    }
}
