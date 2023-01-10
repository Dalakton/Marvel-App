package me.project.marvelapp.domain.model.comic

import com.google.gson.annotations.SerializedName
import me.project.marvelapp.domain.model.thumbnail.Thumbnail
import java.io.Serializable

data class ComicModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
) : Serializable
