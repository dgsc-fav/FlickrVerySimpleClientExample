package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrStaticApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.cache.LastRecentCache
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository.PhotosListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * todo split [PhotosListRepository] to interface and implementation and move to java domain module (or left as is with injected construtor)
 * todo split [FlickrPhotosListProvider] to interface and implementation and move to java domain module (or left as is with injected construtor)
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePhotosListRepository(photosListProvider: FlickrPhotosListProvider, lastRecentCache: LastRecentCache): PhotosListRepository {
        return PhotosListRepository(photosListProvider, lastRecentCache)
    }

    @Provides
    @Singleton
    fun providePhotosListProvider(flickrRestApi: FlickrRestApi, flickrStaticApi: FlickrStaticApi): FlickrPhotosListProvider {
        return FlickrPhotosListProvider(flickrRestApi, flickrStaticApi)
    }

}