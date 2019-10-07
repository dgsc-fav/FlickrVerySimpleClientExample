package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.cache

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.MainDatabase
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.dao.LastRecentDao
import javax.inject.Inject
import javax.inject.Singleton

private const val useInMemory = false

@Singleton
class LastRecentCache
@Inject
constructor(mainDatabase: MainDatabase) {

    // in-memory cache
    private val inMemory: MutableList<PhotoModel> = mutableListOf()
    // in-db cache
    private val inPermanent: LastRecentDao = mainDatabase.lastRecentDao()

    fun remove() {
        inMemory.clear()
        inPermanent.cleatLastRecent()
    }

    fun get(): List<PhotoModel>? {
        inMemory
            .takeIf {
                // return only with data
                it.isNotEmpty()
            }
            ?.let {
                return it
            }

        return inPermanent.getLastRecent()
            .takeIf {
                // return only with data
                it.isNotEmpty()
            }
            ?.also {
                // add them to in-memory cache
                if (useInMemory) inMemory.addAll(it)
            }
    }

    fun get(photoId: String): PhotoModel? {
        return inMemory.firstOrNull {
            it.photoId == photoId
        } ?: inPermanent.getLastRecent(photoId)
    }

    fun put(photoModels: List<PhotoModel>) {
        // clear all cached
        remove()

        inPermanent.insertLastRecent(photoModels)
        if (useInMemory) inMemory.addAll(photoModels)
    }
}