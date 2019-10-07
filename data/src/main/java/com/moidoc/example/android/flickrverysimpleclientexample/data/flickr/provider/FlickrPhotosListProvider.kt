package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider

import androidx.annotation.WorkerThread
import com.moidoc.example.android.flickrverysimpleclientexample.data.BuildConfig
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.Photo
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotosResponse
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.statOk
import retrofit2.Response

class FlickrPhotosListProvider(private val flickrRestApi: FlickrRestApi) {

    @WorkerThread
    fun getRecentPhotos(count: Int): List<PhotoModel> {
        val response: Response<PhotosResponse> = flickrRestApi.getRecent(BuildConfig.FLICKR_API_KEY).execute()
        if (response.isSuccessful) {
            val responseBody: PhotosResponse = response.body() ?: throw RuntimeException("Empty body")

            val stat = responseBody.stat

            if (stat == statOk) {
                val models: List<PhotoModel> = responseBody
                    .photos
                    .photo
                    .take(count)
                    .map { photo ->
                        return@map PhotoModel(
                            id = null,
                            photoId = photo.id,
                            url = makeStaticUrl(photo)
                        )
                    }
                return models
            } else {
                throw RuntimeException(responseBody.message)
            }
        } else {
            throw RuntimeException(response.errorBody()?.string() ?: "An error occurred")
        }
    }

    private fun makeStaticUrl(photo: Photo): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}