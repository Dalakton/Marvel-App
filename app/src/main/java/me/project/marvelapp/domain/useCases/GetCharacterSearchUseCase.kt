package me.project.marvelapp.domain.useCases

import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response

interface GetCharacterSearchUseCase {

    suspend operator fun invoke(nameStartsWith : String? = null) : ResourceState<CharacterModelResponse>
}