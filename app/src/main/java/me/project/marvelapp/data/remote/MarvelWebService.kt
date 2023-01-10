package me.project.marvelapp.data.remote

import kotlinx.coroutines.flow.Flow
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import me.project.marvelapp.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelWebService {

    @GET("characters")
    suspend fun getAllCharacter(
        @Query("nameStartsWith") nameStartsWith: String? = null
    ): Response<CharacterModelResponse>

    @GET("characters/{charactersId}/comics")
    suspend fun getAllComics(
        @Path(
            value = "charactersId",
            encoded = true
        ) characterId: Int
    ): Response<ComicModelResponse>

}