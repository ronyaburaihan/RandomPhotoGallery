package com.englesoft.randomphotogallery.data

import com.englesoft.randomphotogallery.api.GalleryApi
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val galleryApi: GalleryApi
) {
    suspend fun getImages(): Response<List<ImageListResponse>> {
        return galleryApi.getImages()
    }
}