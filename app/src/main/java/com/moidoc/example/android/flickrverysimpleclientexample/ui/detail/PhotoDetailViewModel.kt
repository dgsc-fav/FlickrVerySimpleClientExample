package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.os.Bundle
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel

sealed class PhotoDetailFragmentAction(val bundle: Bundle) {
}

class PhotoDetailViewModel : BaseViewModel<PhotoDetailFragmentAction>() {

    private var photoId: Int? = null

    fun onViewCreated(arguments: Bundle?) {
        photoId = arguments?.getInt("photoId") ?: throw IllegalArgumentException("\"photoId\" is null")


        // get photo data from the database and post data to the observer
    }

}