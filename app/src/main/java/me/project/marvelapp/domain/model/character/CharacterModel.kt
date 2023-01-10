package me.project.marvelapp.domain.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.project.marvelapp.domain.model.thumbnail.Thumbnail
import java.io.Serializable

@Entity(tableName = "characterModel")
data class CharacterModel(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
) : Serializable

