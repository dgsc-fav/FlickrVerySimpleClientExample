package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.moidoc.example.android.flickrverysimpleclientexample.R
import kotlinx.android.synthetic.main.fragment_photos_list.*
import timber.log.Timber

class PhotosListFragment: Fragment() {

    private lateinit var viewModel: PhotosListViewModel

    /** The screen navigation observer */
    private val navigationActionObserver: Observer<in PhotosListFragmentAction> = Observer { action ->
        Timber.v("do action: ${action?.javaClass?.simpleName}, with bundle=${action?.bundle}")

        when (action) {
            is PhotosListFragmentAction.ShowDetailsScreen -> {
                findNavController().navigate(R.id.action_photosListFragment_to_photoDetailFragment, action.bundle)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fill views via kotlinx.android.synthetic
        to_detail.setOnClickListener {
            viewModel.onPhotoClick()
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PhotosListViewModel::class.java)

        /// observe live data
        // screen navigation
        viewModel.navigationAction.observe(this, navigationActionObserver)

        viewModel.onViewCreated(arguments)
    }
}