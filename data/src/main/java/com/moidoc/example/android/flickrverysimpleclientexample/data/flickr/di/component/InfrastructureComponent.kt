package com.moidoc.example.android.flickrverysimpleclientexample.di.component

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrStaticApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.FlickrModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.RepositoryModule
import com.moidoc.example.android.flickrverysimpleclientexample.di.module.NetworkModule
import dagger.Component

@Component(
    modules = [
        RepositoryModule::class,
        FlickrModule::class,
        NetworkModule::class
    ]
)
interface InfrastructureComponent {

    fun getFlickrRestApi(): FlickrRestApi

    fun getFlickrStaticApi(): FlickrStaticApi
}