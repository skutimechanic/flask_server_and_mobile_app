package com.polsl.movielibrary.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.polsl.movielibrary.R
import com.polsl.movielibrary.databinding.FragmentLoginBinding
import com.polsl.movielibrary.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            val login = binding.usernameLoginInput.text.toString()
            val password = binding.passwordLoginInput.text.toString()

            viewModel.login(login = login, password = password)
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            if (it) {
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(
                        activity,
                        R.string.invalid_credentials,
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
