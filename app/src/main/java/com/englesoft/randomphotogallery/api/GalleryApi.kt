package com.englesoft.randomphotogallery.api

import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.englesoft.randomphotogallery.utils.Constants.CLIENT_ID
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {

    @GET("photos")
    suspend fun getImages(@Query("client_id") clientId: String = CLIENT_ID): Response<List<ImageListResponse>>
}