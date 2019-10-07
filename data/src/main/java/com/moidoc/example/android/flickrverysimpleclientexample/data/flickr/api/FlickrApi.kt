package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrRestApi {
    companion object {
        val restApiBaseUrl: String
            get() = "https://api.flickr.com/services/rest/"
    }

    @GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    fun getRecent(@Query("api_key") apiKey: String): Call<PhotosResponse>
}