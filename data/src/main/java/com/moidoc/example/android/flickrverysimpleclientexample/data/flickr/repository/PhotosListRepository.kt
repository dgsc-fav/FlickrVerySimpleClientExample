package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository

import androidx.annotation.WorkerThread
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.cache.LastRecentCache
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider

class PhotosListRepository
constructor(private val flickrPhotosListProvider: FlickrPhotosListProvider, private val lastRecentCache: LastRecentCache) {

    @WorkerThread
    fun getRecentPhotos(refresh: Boolean, page: Int = 0, count: Int = Int.MAX_VALUE): List<PhotoModel> {
        if (refresh) {
            lastRecentCache.remove()
        }

        // try get cached
        lastRecentCache.get()?.let { cached ->
            return cached
        }

        // otherwise get from the internet
        return flickrPhotosListProvider.getRecentPhotos(count).also {
            // and add to the cache
            lastRecentCache.put(it)
        }
    }

    @WorkerThread
    fun getPhoto(photoId: String) : PhotoModel? {
        return lastRecentCache.get(photoId)
    }
}