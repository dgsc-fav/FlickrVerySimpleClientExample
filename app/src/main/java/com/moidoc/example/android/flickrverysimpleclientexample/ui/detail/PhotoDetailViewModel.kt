package com.moidoc.example.android.flickrverysimpleclientexample.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moidoc.example.android.flickrverysimpleclientexample.App
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.repository.PhotosListRepository
import com.moidoc.example.android.flickrverysimpleclientexample.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

sealed class PhotoDetailFragmentAction(val bundle: Bundle) {
}

class PhotoDetailViewModel : BaseViewModel<PhotoDetailFragmentAction>() {

    @Inject
    lateinit var repository: PhotosListRepository
    /**
     *
     */
    private val photoUrl: MutableLiveData<String> = MutableLiveData()

    private var photoId: String? = null

    init {
        App.appComponent.inject(this)
    }

    fun onViewCreated(context: Context, arguments: Bundle?) {
        photoId = arguments?.getString(context.getString(R.string.key_photo_id)) ?: throw IllegalArgumentException("\"photoId\" is null")

        // get photo data from the repository and post data to the observer

        addDisposableTask(
            tag = getPhotoTag,
            coroutineDispatcher = Dispatchers.IO,
            block = {
                repository.getPhoto(photoId!!)?.let {
                    photoUrl.postValue(it.url)
                }
                clearTask(getPhotoTag)
            })
    }

    fun watchPhotoUrl(): LiveData<String> = photoUrl

    companion object {
        const val getPhotoTag = "getPhoto"
    }
}