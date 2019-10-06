package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.view.View
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.Photo

class PhotosListItem(
    // an database _id
    val id: Int,
    // the flickr photo model (used direct from the models. if you a Clean Arch adept you can map [Photo] to other data class)
    val photo: Photo
) {
    var adapterPosition: Int? = null
    var sharedView: View? = null
}