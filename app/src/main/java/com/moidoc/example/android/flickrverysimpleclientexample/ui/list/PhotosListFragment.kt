package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.content.res.Configuration
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter.AdapterCallback
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter.ClickType
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.decoration.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_photos_list.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Transition animations was inspired by https://github.com/android/animation-samples
 */
class PhotosListFragment : Fragment() {

    private lateinit var viewModel: PhotosListViewModel

    private lateinit var adapter: PhotosListAdapter<RecyclerView.ViewHolder>
    
    private lateinit var enterTransitionStarted: AtomicBoolean

    /**
     * The flag to allow call [Fragment.postponeEnterTransition] if exists shared element when we go back from the [PhotoDetailFragment]
     */
    private var existSharedElement: Boolean = false

    /** The screen navigation observer */
    private val navigationActionObserver: Observer<in PhotosListFragmentAction> = Observer { action ->
        when (action) {
            is PhotosListFragmentAction.ShowDetailsScreen -> {
                findNavController().navigate(
                    R.id.action_photosListFragment_to_photoDetailFragment,
                    action.bundle,
                    null,
                    action.extras
                )
            }
        }
    }

    private val stateObserver: Observer<in PhotosListUpdateState> = Observer { photosListUpdateState ->

        Timber.e("stateObserver=$photosListUpdateState")

        when (photosListUpdateState) {
            PhotosListUpdateState.CLEAR -> {
                adapter.clear()
            }
            PhotosListUpdateState.LOADING -> {
                swipe_to_refresh.isRefreshing = true
            }
            PhotosListUpdateState.LOADED -> {
                swipe_to_refresh.isRefreshing = false
            }
            else -> {
                // do nothing
            }
        }
    }

    private val listObserver: Observer<in List<PhotosListItem>> = Observer {
        Timber.e("listObserver=${it.size}")
        // add items. clearing adapter if refreshing will be called in viewModel.photosListUpdateState observer
        adapter.addItems(it)

        if (::enterTransitionStarted.isInitialized && !enterTransitionStarted.getAndSet(true)) {
            // https://medium.com/androiddevelopers/fragment-transitions-ea2726c3f36f
            // Data is loaded so lets wait for our parent to be drawn
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                // Parent has been drawn. Start transitioning!
                Timber.v("startPostponedEnterTransition")
                startPostponedEnterTransition()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos_list, container, false)

        prepareTransitions()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fill views via kotlinx.android.synthetic
        swipe_to_refresh.setOnRefreshListener {
            viewModel.updateList(true)
        }

        adapter = PhotosListAdapter(requireContext(), object : AdapterCallback<PhotosListItem> {
            override fun invoke(clickType: ClickType, photosListItem: PhotosListItem) {
                when (clickType) {
                    ClickType.CLICK -> viewModel.onPhotoClick(requireContext(), photosListItem)
                    ClickType.LONG_CLICK -> viewModel.onPhotoLongClick(photosListItem)
                }
            }
        })

        val screenOrientation = requireContext().resources.configuration.orientation

        // as defined in [README.md)
        val (spanCount, spacingPercentage) = when (screenOrientation) {
            Configuration.ORIENTATION_PORTRAIT -> Pair(2, 0.1f)
            Configuration.ORIENTATION_LANDSCAPE -> Pair(4, 0.04f)
            else -> Pair(0, 0.0f) // never happens
        }

        recycler_view.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recycler_view.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = spanCount,
                spacingPercentage = spacingPercentage,
                includeEdge = true
            )
        )
        recycler_view.adapter = adapter
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PhotosListViewModel::class.java)
        
        // postpone only of NOT first creation otherwise the fragment will not observe viewModel.
        // Wrong fragment state for observing?
        // so we obtain that there is exists shared element and if it not exist then do not call postponeEnterTransition
        if (existSharedElement || savedInstanceState?.getBoolean("existSharedElement") == true) {
            Timber.v("postponeEnterTransition")
            enterTransitionStarted = AtomicBoolean()
            postponeEnterTransition()
        }
        
        /// observe live data
        // screen navigation observer
        viewModel.navigationAction.observe(viewLifecycleOwner, navigationActionObserver)
        // loading state observer
        viewModel.photosListUpdateState.observe(viewLifecycleOwner, stateObserver)
        // photos list observer
        viewModel.photosList.observe(viewLifecycleOwner, listObserver)

        viewModel.onViewCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save the flag
        outState.putBoolean("existSharedElement", existSharedElement)
    }

    /**
     * Prepares the shared element transition
     */
    private fun prepareTransitions() {

        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    // Locate the ViewHolder to which transition will return
                    ///
                    existSharedElement = false
                    val clickedPhotosListItem = viewModel.clickedPhotosListItem

                    val adapterPosition = clickedPhotosListItem?.adapterPosition ?: return

                    val selectedViewHolder = recycler_view.findViewHolderForAdapterPosition(adapterPosition)

                    if (selectedViewHolder?.itemView == null) {
                        return
                    }

                    existSharedElement = true

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = selectedViewHolder.itemView.findViewById(R.id.item_image)
                }
            })
    }
}