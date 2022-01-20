package com.polsl.movielibrary.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: BaseViewModel> : Fragment() {

    abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadedFailed.observe(viewLifecycleOwner) {
            Toast.makeText(
                activity,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.noInternet.observe(viewLifecycleOwner) {
            Toast.makeText(
                activity,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}