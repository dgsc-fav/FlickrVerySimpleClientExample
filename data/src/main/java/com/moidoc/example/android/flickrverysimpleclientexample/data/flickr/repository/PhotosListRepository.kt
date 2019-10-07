package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository

import androidx.annotation.WorkerThread
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosListRepository
@Inject
constructor(private val flickrPhotosListProvider: FlickrPhotosListProvider) {

    init {
        Timber.e("\n\n\nPhotosListRepository($flickrPhotosListProvider\n\n\n")
    }

    val cache: MutableMap<Pair<Int, Int>, List<PhotoModel>> = mutableMapOf()

    @WorkerThread
    fun getRecentPhotos(refresh: Boolean, page: Int = 0, count: Int = Int.MAX_VALUE): List<PhotoModel> {

        val cacheKey = Pair(page, count)

        if (refresh) {
            cache.remove(cacheKey)
        }

        cache[cacheKey]?.let { cached ->
            Timber.v("return cached")
            return cached
        }
        
        val list = flickrPhotosListProvider.getRecentPhotos(page, count)
        cache[cacheKey] = list
        return list
    }

    @WorkerThread
    fun getPhoto(id: String) : PhotoModel? {
        return cache.values.flatten().firstOrNull {
            it.photo.id == id
        }
    }
}