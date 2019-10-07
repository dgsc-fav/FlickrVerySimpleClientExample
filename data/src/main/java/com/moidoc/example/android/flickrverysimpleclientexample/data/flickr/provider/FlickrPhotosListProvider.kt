package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.provider

import androidx.annotation.WorkerThread
import com.moidoc.example.android.flickrverysimpleclientexample.data.BuildConfig
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrRestApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.api.FlickrStaticApi
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.Photo
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotosResponse
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickrPhotosListProvider
@Inject
constructor(private val flickrRestApi: FlickrRestApi, private val flickrStaticApi: FlickrStaticApi) {

    init {
        Timber.e("\n\n\nFlickrPhotosListProvider\n\n\n")
    }

    @WorkerThread
    fun getRecentPhotos(page: Int, count: Int): List<PhotoModel> {
        val response: Response<PhotosResponse> = flickrRestApi.getRecent(BuildConfig.FLICKR_API_KEY).execute()
        if (response.isSuccessful) {
            val responseBody: PhotosResponse = response.body() ?: throw RuntimeException("Empty body")

            val stat = responseBody.stat

            // todo hardcoded string
            if (stat == "ok") {
                val models: List<PhotoModel> = responseBody
                    .photos
                    .photo
                    .take(count)
                    .map { photo ->
                        return@map PhotoModel(
                            id = null,
                            photo = photo,
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