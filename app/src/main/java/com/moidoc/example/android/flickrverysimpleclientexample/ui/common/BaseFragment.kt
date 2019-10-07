package com.moidoc.example.android.flickrverysimpleclientexample.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel

abstract class BaseFragment<A, VM : BaseViewModel<A>> : Fragment() {

    protected lateinit var viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()

        viewModel.error.observe(viewLifecycleOwner, Observer {
            showErrorSnackBar(it)
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            showMessageSnackBar(it.toString())
        })
    }

    open fun showErrorSnackBar(t: Throwable) {
        showSnackbar(t.toString())
    }

    open fun showMessageSnackBar(s: String) {
        showSnackbar(s)
    }

    open fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    abstract fun initViewModel()
}