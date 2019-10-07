package com.moidoc.example.android.flickrverysimpleclientexample.data.storage.dao

import androidx.room.*
import com.moidoc.example.android.flickrverysimpleclientexample.data.flickr.model.PhotoModel

@Dao
interface LastRecentDao {

    @Query("DELETE FROM last_recent")
    fun cleatLastRecent(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Transaction
    fun insertLastRecent(photoModels: List<PhotoModel>)

    @Query("SELECT * FROM last_recent WHERE id = :id")
    fun getLastRecent(id: Int): PhotoModel?

    @Query("SELECT * FROM last_recent WHERE photoId = :photoId")
    fun getLastRecent(photoId: String): PhotoModel?

    @Query("SELECT * FROM last_recent")
    fun getLastRecent(): List<PhotoModel>
}