package me.project.marvelapp.domain.useCases

import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.presentation.state.ResourceState

class GetListCharacterUseCaseImpl(private val repository: MarvelRepository) :
    GetListCharacterUseCase {


    //  tratamento da resposta da api
    // caso sendo .sucesso ou Erro , envio o estado.

    override suspend fun invoke(): ResourceState<CharacterModelResponse> {

        val response = repository.getAllCharacters()

        if (response.isSuccessful) {
            response.body()?.let { character ->
                return ResourceState.Sucess(character)
            }
        }
        return ResourceState.Error(response.message())
    }
}

