package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
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

    private val photoId: Int by lazy { arguments?.getInt(requireContext().getString(R.string.key_photo_id)) ?: -1 }

    private lateinit var viewModel: PhotoDetailViewModel

    /** The screen navigation observer */
    private val navigationActionObserver: Observer<in PhotoDetailFragmentAction> = Observer { action ->
        Timber.v("do action: ${action?.javaClass?.simpleName}, with bundle=${action?.bundle}")

        when (action) {
            // no action yet
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.image_shared_element_transition)

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        details_image.transitionName = photoId.toString()
        details_image.setImageResource(R.drawable.mm)

        startPostponedEnterTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)

        /// observe live data
        // screen navigation
        viewModel.navigationAction.observe(viewLifecycleOwner, navigationActionObserver)

        viewModel.onViewCreated(requireContext(), arguments)

    }

    /// ToolbarResolver functions

    override fun hasBackKey(): Boolean = true
}