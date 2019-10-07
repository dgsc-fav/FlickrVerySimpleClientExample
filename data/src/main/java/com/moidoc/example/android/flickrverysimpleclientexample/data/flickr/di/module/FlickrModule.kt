package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrStaticApi
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

    @Provides
    internal fun provideFlickrStaticApi(retrofit: Retrofit): FlickrStaticApi {
        // for the static api left the base url as is - it will be ignored by the api methods
        return retrofit.create(FlickrStaticApi::class.java)
    }
}