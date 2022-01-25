package com.polsl.movielibrary.ui.userMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonGetMovies.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_user_movies_to_navigation_login)
        }

        viewModel.isUserLoggedIn()

        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getUserInfo()
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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
