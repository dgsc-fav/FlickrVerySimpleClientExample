package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.view.View

class PhotosListItem(
    // an id of the flickr photo item
    val photoId: String,
    // url of the photo
    val url: String
) {
    // for transition animation
    var adapterPosition: Int? = null
    var sharedView: View? = null
}