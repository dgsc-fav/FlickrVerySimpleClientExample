package com.moidoc.example.android.flickrverysimpleclientexample.di.component

import com.moidoc.example.android.flickrverysimpleclientexample.ui.detail.PhotoDetailViewModel
import com.moidoc.example.android.flickrverysimpleclientexample.ui.list.PhotosListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [InfrastructureComponent::class])
interface AppComponent {
    fun inject(photosListViewModel: PhotosListViewModel)
    fun inject(photoDetailViewModel: PhotoDetailViewModel)
}