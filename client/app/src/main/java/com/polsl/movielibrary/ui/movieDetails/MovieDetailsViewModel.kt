package com.polsl.movielibrary.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.movielibrary.api.models.MovieDetailsItemModel
import com.polsl.movielibrary.api.models.UserMovieDetailsItemModel
import com.polsl.movielibrary.brokers.RepositoryInvoker
import com.polsl.movielibrary.recource.Resource
import com.polsl.movielibrary.repositories.AuthRepository
import com.polsl.movielibrary.repositories.MoviesRepository
import com.polsl.movielibrary.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val EXAMPLE_MOVIE = UserMovieDetailsItemModel(
        movie = MovieDetailsItemModel(
                id = 1,
                title = "Venom",
                description = "Kiedy Eddie Brock zdobywa moce symbionta, zmuszony jest uwolnić swoje alter-ego \\\"Venoma\\\", by ratować własne życie.",
                image_link = "https://m.media-amazon.com/images/I/5104DBeCJRL._AC_SY780_.jpg",
                rating_sum = 89,
                number_of_votes = 567,
                category = "Akcja",
                director = "Ruben Fleischer",
                year = 2018,
                country = "Chiny"
        ),
        rate = 75
)

class MovieDetailsViewModel(
        private val repository: MoviesRepository,
        private val repositoryInvoker: RepositoryInvoker,
) : BaseViewModel() {

    private val _movieDetails = MutableLiveData<UserMovieDetailsItemModel>()

    val movieDetails: LiveData<UserMovieDetailsItemModel> = _movieDetails

    fun loadDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showLoader()
                //TODO fetch details
                _movieDetails.postValue(EXAMPLE_MOVIE)
                hideLoader()
            }
        }
    }
}