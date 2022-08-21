package com.englesoft.randomphotogallery.data

import com.englesoft.randomphotogallery.data.database.GalleryDao
import com.englesoft.randomphotogallery.data.model.ImageItem
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val galleryDao: GalleryDao
) {

    fun loadImages(): Flow<List<ImageListResponse>> {
        return galleryDao.getAllImages()
    }

    suspend fun insertImages(imageList: List<ImageListResponse>) {
        galleryDao.insertImages(imageList)
    }
}