package com.polsl.movielibrary.ui.userMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polsl.movielibrary.R
import com.polsl.movielibrary.api.models.UserMovieListItemModel
import com.polsl.movielibrary.databinding.FragmentUserMoviesBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserMoviesFragment : BaseFragment<UserMoviesViewModel>() {

    override val viewModel: UserMoviesViewModel by viewModel()
    private var _binding: FragmentUserMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            binding.userLoggedInText.text = getString(R.string.user_logged_in_text, it)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            setMoviesList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMoviesList(movieListItems: List<UserMovieListItemModel>) {
        with(binding.userMoviesListRecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = UserMovieAdapter { viewModel.handleOnItemClick(it) }.apply {
                setItems(movieListItems)
            }
        }
    }
}
