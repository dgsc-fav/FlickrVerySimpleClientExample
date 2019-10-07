package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository.PhotosListRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * todo split [PhotosListRepository] to interface and implementation and move to java domain module (or left as is with injected construtor)
 * todo split [FlickrPhotosListProvider] to interface and implementation and move to java domain module (or left as is with injected construtor)
 */
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providePhotosListRepository(photosListRepository: PhotosListRepository): PhotosListRepository

    @Binds
    @Singleton
    abstract fun providePhotosListProvider(photosListProvider: FlickrPhotosListProvider): FlickrPhotosListProvider

}