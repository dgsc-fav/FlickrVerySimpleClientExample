package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api

import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotosResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface FlickrRestApi {
    companion object {
        val restApiBaseUrlR: String
            get() = "https://api.flickr.com/services/rest/"
    }


    //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=da9d38d3dee82ec8dda8bb0763bf5d9c&format=json&nojsoncallback=1
    @GET("/?method=flickr.photos.getRecent&api_key={api_key}&format=json&nojsoncallback=1")
    fun getRecent(@Path("api_key") api_key: String): Call<PhotosResponse>
}

interface FlickrStaticApi {
    //("https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg")
    @GET
    fun getImage(@Url url: String): Call<ResponseBody>
}