package me.project.marvelapp.domain.useCases

import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response

class GetCharacterSarchUseCaseImpl(val repository: MarvelRepository) : GetCharacterSearchUseCase {

    // tratamento da resposta e retornando o estado.
    override suspend fun invoke(nameStartsWith: String?): ResourceState<CharacterModelResponse> {
        val response = repository.searchForCharacters(nameStartsWith)
        if (response.isSuccessful) {
            response.body()?.let {
                return ResourceState.Sucess(it)
            }
        }
        return ResourceState.Error(response.message())
    }
}

