package me.project.marvelapp.domain.useCases

import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response

interface GetComicsUseCase {

    suspend operator fun invoke(characterId : Int) : ResourceState<ComicModelResponse>
}