package com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.di.module

import android.content.Context
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.MainDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val databaseCreator: (Context) -> MainDatabase) {
    @Provides
    @Singleton
    fun provideDatabase(context: Context) = databaseCreator(context)
}