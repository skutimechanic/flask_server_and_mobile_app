package com.polsl.movielibrary.ui.allMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.polsl.movielibrary.R
import com.polsl.movielibrary.databinding.FragmentAllMoviesBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import com.polsl.movielibrary.utils.setOnBackPressed
import com.polsl.movielibrary.utils.showDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMoviesFragment : BaseFragment<AllMoviesViewModel>() {

    override val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = MovieAdapter(
        clickItemListener = { navigateToDetails(it) },
        clickFavoriteListener = { viewModel.addMovieToUserList(it) }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnBackPressed {
            showTurnOffDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        binding.moviesListRecyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
        viewModel.addToFavoriteFinished.observe(viewLifecycleOwner) {
            if (it) {
                val toast = Toast.makeText(
                    requireContext(),
                    R.string.toast_movie_added_to_favortie,
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetails(movieId: Int) {
        val action =
            AllMoviesFragmentDirections.actionNavigationAllMoviesToNavigationMovieDetails(movieId)
        findNavController().navigate(action)
    }

    private fun showTurnOffDialog() {
        showDialog(
            title = "Do you want to exit app?",
            positiveButton = "Yes" to { requireActivity().finish() },
            negativeButton = "Stay" to {}
        )
    }
}
