package com.moidoc.example.android.flickrverysimpleclientexample.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel
import com.moidoc.example.android.flickrverysimpleclientexample.data.storage.dao.LastRecentDao

const val DATABASE_NAME = "db.db"

// todo SQLiteLog: (283) recovered 19 frames from WAL file /data/data/com.moidoc.example.android.flickrverysimpleclientexample/databases/db.db-wal
@Database(
    entities = [PhotoModel::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun lastRecentDao(): LastRecentDao
}