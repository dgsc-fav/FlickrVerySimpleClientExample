package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.view.View

class PhotosListItem(
    // an database _id
    val id: Long,
    // the flickr photo model (used direct from the models. if you a Clean Arch adept you can map [Photo] to other data class)
    val photoId: String,
    // url of the photo
    val url: String
) {
    // for transition animation
    var adapterPosition: Int? = null
    var sharedView: View? = null
}