package com.moidoc.example.android.flickrverysimpleclientexample

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.ContextModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.DatabaseModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.FlickrModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module.RepositoryModule
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.DATABASE_NAME
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.MainDatabase
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

        val databaseCreator = { context: Context ->
            Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        infrastructureComponent = DaggerInfrastructureComponent.builder()
            .contextModule(ContextModule(this))
            .repositoryModule(RepositoryModule())
            .flickrModule(FlickrModule())
            .networkModule(NetworkModule("http://mock.domain/"))
            .databaseModule(DatabaseModule(databaseCreator))
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