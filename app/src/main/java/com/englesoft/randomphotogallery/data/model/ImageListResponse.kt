package com.englesoft.randomphotogallery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images")
data class ImageListResponse(
    @PrimaryKey
    val id: String,
    @SerializedName("urls")
    val image: ImageItem = ImageItem()
)