package com.moidoc.example.android.flickrverysimpleclientexample

import android.app.Application
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.FlickrModule
import com.moidoc.example.android.flickrverysimpleclientexample.di.component.AppComponent
import com.moidoc.example.android.flickrverysimpleclientexample.di.component.DaggerAppComponent
import com.moidoc.example.android.flickrverysimpleclientexample.di.component.DaggerInfrastructureComponent
import com.moidoc.example.android.flickrverysimpleclientexample.di.component.InfrastructureComponent
import com.moidoc.example.android.flickrverysimpleclientexample.di.module.NetworkModule
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        // initialize dagger's components
        initComponents()
    }

    private fun initComponents() {

        infrastructureComponent = DaggerInfrastructureComponent.builder()
            .flickrModule(FlickrModule())
            .networkModule(NetworkModule("http://mock.domain/"))
            .build()

        appComponent = DaggerAppComponent.builder()
            .infrastructureComponent(infrastructureComponent)
            .build()

    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var infrastructureComponent: InfrastructureComponent
    }
}