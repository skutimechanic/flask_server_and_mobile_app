package com.polsl.movielibrary.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.polsl.movielibrary.R
import com.polsl.movielibrary.databinding.FragmentMovieDetailsBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import com.polsl.movielibrary.utils.setOnBackPressed
import com.polsl.movielibrary.utils.showDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel>() {

    override val viewModel: MovieDetailsViewModel by viewModel()
    private var _binding: FragmentMovieDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnBackPressed {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            binding.movie = it
        }

        binding.adminDeleteMovieButton.setOnClickListener {
            showDialog(
                    title = getString(R.string.dialog_delete_movie_title),
                    message = getString(R.string.dialog_delete_movie_message, binding.movie!!.movie.title),
                    positiveButton = getString(R.string.yes) to { viewModel.deleteMovie(binding.movie!!.movie.id) },
                    negativeButton = getString(R.string.no) to {}
            )
        }

        viewModel.deleteFinished.observe(viewLifecycleOwner) {
            if (it) {
                val toast = Toast.makeText(
                        requireContext(),
                        R.string.toast_movie_deleted,
                        Toast.LENGTH_SHORT
                )
                toast.show()
                findNavController().popBackStack()
            }
        }

        binding.addMovieButton.setOnClickListener {
            if (binding.movie!!.isUserMovie) {
                viewModel.updateUserRate(binding.movie!!.movie.id, binding.movieMyRateSlider.value)
            } else {
                viewModel.addMovieToUserList(binding.movie!!.movie.id)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val safeArgs = MovieDetailsFragmentArgs.fromBundle(it)
            viewModel.checkUserPrivilege()
            viewModel.loadDetails(safeArgs.movieId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}