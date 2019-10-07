package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.view.View
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.Photo

class PhotosListItem(
    // an database _id
    val id: Long,
    // the flickr photo model (used direct from the models. if you a Clean Arch adept you can map [Photo] to other data class)
    val photo: Photo
) {
    // for transition animation
    var adapterPosition: Int? = null
    var sharedView: View? = null

    // url of the photo
    var url: String? = null
}