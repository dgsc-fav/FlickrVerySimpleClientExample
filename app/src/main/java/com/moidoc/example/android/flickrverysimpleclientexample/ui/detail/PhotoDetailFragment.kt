package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.ToolbarHolder
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.ToolbarResolver
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import timber.log.Timber

class PhotoDetailFragment : Fragment(),
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)

        /// observe live data
        // screen navigation
        viewModel.navigationAction.observe(viewLifecycleOwner, navigationActionObserver)

        viewModel.watchPhotoUrl().observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load(it)
                .fitCenter()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(details_image)
        })

        viewModel.onViewCreated(requireContext(), arguments)

    }

    override fun onResume() {
        super.onResume()

        (activity as? ToolbarHolder)?.updateToolbar()
    }

    /// ToolbarResolver functions

    override fun hasBackKey(): Boolean = true
}