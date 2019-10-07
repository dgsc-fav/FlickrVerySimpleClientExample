package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "LastRecent",
    primaryKeys = ["id"],
    indices = [Index(value = ["id", "photoId"], unique = true)]
)
data class PhotoModel(
    // an database _id
    var id: Int?,
    // [Photo.id]
    var photoId: String,
    // url of the photo
    var url: String
)