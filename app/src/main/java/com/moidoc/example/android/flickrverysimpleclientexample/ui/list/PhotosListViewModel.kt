package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.Photo
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel
import timber.log.Timber

sealed class PhotosListFragmentAction(val bundle: Bundle, val extras: FragmentNavigator.Extras?) {
    /**
     *  An action to open a photo details screen
     *  [bundle] must contains [photoId] integer key with id of the photo
     *  (the key name "photoId" defined in arguments for "PhotoDetailFragment" in the [navigation/nav_graph.xml])
     */
    class ShowDetailsScreen(bundle: Bundle, extras: FragmentNavigator.Extras?) : PhotosListFragmentAction(bundle, extras)
}

enum class PhotosListUpdateState {
    CLEAR, LOADING, LOADED
}

class PhotosListViewModel : BaseViewModel<PhotosListFragmentAction>() {
    // todo remove with functions
    private val _photosListUpdateState: MutableLiveData<PhotosListUpdateState> = MutableLiveData()
    val photosListUpdateState: LiveData<PhotosListUpdateState> = _photosListUpdateState
    private val _photosList: MutableLiveData<List<PhotosListItem>> = MutableLiveData()
    val photosList: LiveData<List<PhotosListItem>> = _photosList

    var clickedPhotosListItem: PhotosListItem? = null
        private set

    init {
        Timber.e("PhotosListViewModel")

        // https://devcolibri.com/5-common-mistakes-when-using-architecture-components/
        updateList(true)
    }

    /**
     * [savedInstanceState] defines this is first start or not
     */
    fun onViewCreated(savedInstanceState: Bundle?) {
        // no arguments need for the photos list
        Timber.w("onViewCreated: $savedInstanceState")
    }

    fun updateList(refresh: Boolean) {
        Timber.w("updateList: $refresh")
        // todo test only
        if (refresh) {
            _photosListUpdateState.value = PhotosListUpdateState.CLEAR

            Timber.w("_photosListUpdateState=${_photosListUpdateState.value}")
        }
        _photosListUpdateState.value = PhotosListUpdateState.LOADING
        Timber.w("_photosListUpdateState=${_photosListUpdateState.value}")

        Handler().postDelayed(Runnable {
            val items = IntRange(0, 19).map {
                PhotosListItem(id = it, photo = Photo.empty)
            }

            _photosList.postValue(items)

            _photosListUpdateState.postValue(PhotosListUpdateState.LOADED)

            Timber.w("_photosListUpdateState=${_photosListUpdateState.value}")
        }, 20)
    }

    fun onPhotoClick(context: Context, photosListItem: PhotosListItem) {
        clickedPhotosListItem = photosListItem

        var extras: FragmentNavigator.Extras? = null

        photosListItem.sharedView?.let { sharedView ->
            val transitionName = photosListItem.id.toString()

            extras = FragmentNavigatorExtras(
                sharedView to transitionName
            )
        }

        _navigationAction.value = PhotosListFragmentAction.ShowDetailsScreen(
            bundle = Bundle().apply {
                putInt(context.getString(R.string.key_photo_id), photosListItem.id)
            },
            extras = extras
        )
    }

    fun onPhotoLongClick(photosListItem: PhotosListItem) {

    }
}