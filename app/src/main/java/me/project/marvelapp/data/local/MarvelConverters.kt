package me.project.marvelapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import me.project.marvelapp.domain.model.thumbnail.Thumbnail

class MarvelConverters {

    @TypeConverter
    fun fromThumbnail(thumbnail : Thumbnail): String = Gson().toJson(thumbnail)

    @TypeConverter
    fun toThumbnail(thumbnail: String) : Thumbnail =
        Gson().fromJson(thumbnail, Thumbnail::class.java)
}