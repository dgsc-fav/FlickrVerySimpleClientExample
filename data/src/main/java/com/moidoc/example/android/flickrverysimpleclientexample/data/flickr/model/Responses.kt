package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model

import androidx.annotation.Keep

@Keep
data class PhotosResponse(
    val photos: Photos,
    val stat: String,
    val code: Int,
    val message: String
)

@Keep
data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)

@Keep
data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)