package com.moidoc.example.android.flickrverysimpleclientexample.di.component

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.ContextModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.DatabaseModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.FlickrModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.RepositoryModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider.FlickrPhotosListProvider
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository.PhotosListRepository
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.MainDatabase
import com.moidoc.example.android.flickrverysimpleclientexample.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        ContextModule::class,
        FlickrModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface InfrastructureComponent {

    fun flickrRestApi(): FlickrRestApi

    fun mainDatabase(): MainDatabase

    fun flickrPhotosListProvider(): FlickrPhotosListProvider

    fun photosListRepository(): PhotosListRepository
}