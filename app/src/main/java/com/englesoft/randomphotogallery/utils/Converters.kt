package com.englesoft.randomphotogallery.utils

import androidx.room.TypeConverter
import com.englesoft.randomphotogallery.data.model.ImageItem
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromImageListResponse(imageItem: ImageItem): String? {
        return Gson().toJson(imageItem)
    }

    @TypeConverter
    fun fromStringToImageListResponse(value: String?): ImageItem {
        return Gson().fromJson(value, ImageItem::class.java)
    }
}