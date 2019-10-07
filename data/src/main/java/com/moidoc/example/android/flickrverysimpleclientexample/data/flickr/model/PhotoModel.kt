package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model

data class PhotoModel(
    // an database _id
    val id: Int?,
    // the flickr photo model (used direct from the models. if you a Clean Arch adept you can map [Photo] to other data class)
    val photo: Photo,
    // url of the photo
    var url: String
)