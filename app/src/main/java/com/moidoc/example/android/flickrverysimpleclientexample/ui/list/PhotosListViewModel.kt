package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.os.Bundle
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel

sealed class PhotosListFragmentAction(val bundle: Bundle) {
    /**
     *  An action to open a photo details screen
     *  [bundle] must contains [photoId] integer key with id of the photo
     *  (the key name "photoId" defined in arguments for "PhotoDetailFragment" in the [navigation/nav_graph.xml])
     */
    class ShowDetailsScreen(bundle: Bundle) : PhotosListFragmentAction(bundle)
}

class PhotosListViewModel : BaseViewModel<PhotosListFragmentAction>() {


    fun onViewCreated(arguments: Bundle?) {
        // no arguments need for the photos list
    }


    fun onPhotoClick() {
        // todo just for test

        _navigationAction.value = PhotosListFragmentAction.ShowDetailsScreen(Bundle().apply { putInt("photoId", 123) })
    }

}