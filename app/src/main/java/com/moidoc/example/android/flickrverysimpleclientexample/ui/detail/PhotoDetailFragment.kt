package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.ToolbarResolver
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import timber.log.Timber

class PhotoDetailFragment: Fragment(),
    ToolbarResolver {

    private lateinit var viewModel: PhotoDetailViewModel

    /** The screen navigation observer */
    private val navigationActionObserver: Observer<in PhotoDetailFragmentAction> = Observer { action ->
        Timber.v("do action: ${action?.javaClass?.simpleName}, with bundle=${action?.bundle}")

        when (action) {
            // no action yet
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text.text = arguments.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)


        /// observe live data
        // screen navigation
        viewModel.navigationAction.observe(this, navigationActionObserver)

        viewModel.onViewCreated(arguments)
    }

    /// ToolbarResolver functions

    override fun hasBackKey(): Boolean = true
}