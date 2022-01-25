package com.polsl.movielibrary.ui.allMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.api.models.MovieApiModel
import com.polsl.movielibrary.databinding.FragmentAllMoviesBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMoviesFragment : BaseFragment<AllMoviesViewModel>() {

    override val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllMoviesBinding? = null
    var adapter: MovieAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            setMoviesList(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMoviesList(movies: List<MovieApiModel>) {
        with(binding.moviesListRecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = MovieAdapter().apply {
                setItems(movies)
            }
        }
    }
}
