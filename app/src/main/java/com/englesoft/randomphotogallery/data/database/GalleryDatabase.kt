package com.englesoft.randomphotogallery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.englesoft.randomphotogallery.utils.Converters

@Database(entities = [ImageListResponse::class], version = 2)
@TypeConverters(Converters::class)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}