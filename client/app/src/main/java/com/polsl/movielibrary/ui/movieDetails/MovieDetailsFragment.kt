package com.polsl.movielibrary.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.polsl.movielibrary.databinding.FragmentMovieDetailsBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import com.polsl.movielibrary.utils.setOnBackPressed
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
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            binding.movie = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val safeArgs = MovieDetailsFragmentArgs.fromBundle(it)

            viewModel.loadDetails(safeArgs.movieId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}