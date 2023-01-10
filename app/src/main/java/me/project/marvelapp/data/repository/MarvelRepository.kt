package me.project.marvelapp.data.repository

import kotlinx.coroutines.flow.Flow
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import retrofit2.Response

interface MarvelRepository {

    suspend fun getAllCharacters() :  Response<CharacterModelResponse>
    suspend fun searchForCharacters(nameStartsWith : String? = null) : Response<CharacterModelResponse>
    suspend fun getComics(characterId : Int) : Response<ComicModelResponse>

   suspend fun insert(characterModel: CharacterModel) : Long
   fun getAll() : Flow<List<CharacterModel>>
   suspend fun delete(characterModel: CharacterModel)
}