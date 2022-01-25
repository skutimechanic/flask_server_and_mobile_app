package com.polsl.movielibrary.ui.dashboard

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.polsl.movielibrary.R
import com.polsl.movielibrary.databinding.FragmentDashboardBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<DashboardViewModel>() {

    override val viewModel: DashboardViewModel by viewModel()
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonGetMovies.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_navigation_login)
        }

        viewModel.isUserLoggedIn()

        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                binding.userLoggedInLayout.visibility = View.VISIBLE
                binding.userNotLoggedInLayout.visibility = View.INVISIBLE
            } else {
                binding.userLoggedInLayout.visibility = View.INVISIBLE
                binding.userNotLoggedInLayout.visibility = View.VISIBLE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
