package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository

import androidx.annotation.WorkerThread
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.cache.LastRecentCache
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider
import timber.log.Timber

class PhotosListRepository
constructor(private val flickrPhotosListProvider: FlickrPhotosListProvider, private val lastRecentCache: LastRecentCache) {

    init {
        Timber.e("\n\n\nPhotosListRepository($flickrPhotosListProvider\n\n$lastRecentCache\n")
    }

    @WorkerThread
    fun getRecentPhotos(refresh: Boolean, page: Int = 0, count: Int = Int.MAX_VALUE): List<PhotoModel> {

        if (refresh) {
            lastRecentCache.remove()
        }

        lastRecentCache.get()?.let { cached ->
            Timber.v("return cached")
            return cached
        }
        
        val list = flickrPhotosListProvider.getRecentPhotos(count)
        lastRecentCache.put(list)
        return list
    }

    @WorkerThread
    fun getPhoto(photoId: String) : PhotoModel? {
        return lastRecentCache.get(photoId)
    }
}