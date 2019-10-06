package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.content.Context
import android.os.Bundle
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel

sealed class PhotoDetailFragmentAction(val bundle: Bundle) {
}

class PhotoDetailViewModel : BaseViewModel<PhotoDetailFragmentAction>() {

    private var photoId: Int? = null

    fun onViewCreated(context: Context, arguments: Bundle?) {
        photoId = arguments?.getInt(context.getString(R.string.key_photo_id)) ?: throw IllegalArgumentException("\"photoId\" is null")


        // get photo data from the database and post data to the observer
    }



}