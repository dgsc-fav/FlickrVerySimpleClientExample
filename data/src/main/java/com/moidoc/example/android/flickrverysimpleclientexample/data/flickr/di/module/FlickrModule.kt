package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * A module for the Flickr service
 */
@Module
class FlickrModule {

    @Provides
    internal fun provideFlickrRestApi(retrofit: Retrofit) : FlickrRestApi {
        // for the rest api change the base url
        return retrofit.newBuilder()
            .baseUrl(FlickrRestApi.restApiBaseUrl)
            .build()
            .create(FlickrRestApi::class.java)
    }
}