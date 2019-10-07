package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotosResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FlickrRestApi {
    companion object {
        val restApiBaseUrl: String
            get() = "https://api.flickr.com/services/rest/"
    }

    @GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    fun getRecent(@Query("api_key") apiKey: String): Call<PhotosResponse>
}

interface FlickrStaticApi {
    //("https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg")
    @GET
    fun getImage(@Url url: String): Call<ResponseBody>
}