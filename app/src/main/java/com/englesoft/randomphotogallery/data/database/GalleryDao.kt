package com.englesoft.randomphotogallery.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englesoft.randomphotogallery.data.model.ImageItem
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {
    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<List<ImageListResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(imageList: List<ImageListResponse>)

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()
}