package me.project.marvelapp.domain.useCases

import kotlinx.coroutines.flow.Flow
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response

interface GetListCharacterUseCase {

    // Está mesma função será utilizada também para a busca dos personagens na aba de pesquisa
    suspend operator fun invoke() : ResourceState<CharacterModelResponse>
}