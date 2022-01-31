package com.polsl.movielibrary.ui.userMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.polsl.movielibrary.R
import com.polsl.movielibrary.databinding.FragmentUserMoviesBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserMoviesFragment : BaseFragment<UserMoviesViewModel>() {

    override val viewModel: UserMoviesViewModel by viewModel()
    private var _binding: FragmentUserMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = UserMovieAdapter(
        clickItemListener = { navigateToDetails(it) },
        clickDeleteListener = { viewModel.deleteMovieFromUserList(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userMoviesListRecyclerView.adapter = adapter

        binding.buttonGetMovies.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_user_movies_to_navigation_login)
        }

        viewModel.isUserLoggedIn()

        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getUserInfo()
                viewModel.loadUSerMovies()
            } else {
                binding.userLoggedInLayout.visibility = View.INVISIBLE
                binding.userNotLoggedInLayout.visibility = View.VISIBLE
            }
        }

        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.userLoggedInLayout.visibility = View.VISIBLE
            binding.userNotLoggedInLayout.visibility = View.INVISIBLE
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }

        viewModel.removedMovie.observe(viewLifecycleOwner) {
            if (it) {
                val toast = Toast.makeText(
                    requireContext(),
                    R.string.toast_movie_removed_from_favortie,
                    Toast.LENGTH_SHORT
                )
                toast.show()
                viewModel.loadUSerMovies()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetails(movieId: Int) {
        val action =
            UserMoviesFragmentDirections.actionNavigationUserMoviesToNavigationMovieDetails(movieId)
        findNavController().navigate(action)
    }
}
