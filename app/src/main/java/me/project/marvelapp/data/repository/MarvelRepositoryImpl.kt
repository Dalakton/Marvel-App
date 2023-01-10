package me.project.marvelapp.data.repository

import kotlinx.coroutines.flow.Flow
import me.project.marvelapp.data.local.MarvelDao
import me.project.marvelapp.data.remote.MarvelWebService
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import retrofit2.Response

class MarvelRepositoryImpl(
    private val serviceapi: MarvelWebService,
    private val dao: MarvelDao
) : MarvelRepository {

    override suspend fun getAllCharacters() = serviceapi.getAllCharacter()
    override suspend fun searchForCharacters(nameStartsWith: String?) =
        serviceapi.getAllCharacter(nameStartsWith)

    override suspend fun getComics(characterId: Int) = serviceapi.getAllComics(characterId)


    override suspend fun insert(characterModel: CharacterModel) = dao.insert(characterModel)
    override fun getAll(): Flow<List<CharacterModel>> = dao.getAll()
    override suspend fun delete(characterModel: CharacterModel) = dao.delete(characterModel)

}