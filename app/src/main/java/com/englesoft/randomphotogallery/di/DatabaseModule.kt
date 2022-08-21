package com.englesoft.randomphotogallery.di

import android.content.Context
import androidx.room.Room
import com.englesoft.randomphotogallery.data.database.GalleryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            GalleryDatabase::class.java,
            "gallery_database"
        ).build()

    @Singleton
    @Provides
    fun provideGalleryDao(database: GalleryDatabase) = database.galleryDao()

}