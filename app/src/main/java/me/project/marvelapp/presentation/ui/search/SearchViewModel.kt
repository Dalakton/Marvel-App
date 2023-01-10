package me.project.marvelapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.useCases.GetCharacterSearchUseCase
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response
import java.io.IOException

class SearchViewModel(
    private val getCharacterSearchUseCase: GetCharacterSearchUseCase
) : ViewModel() {

    private val _searchCharacter =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Empty())
    val searchCharacter: StateFlow<ResourceState<CharacterModelResponse>> = _searchCharacter

    fun fetch(nameStartsWith: String) = viewModelScope.launch {
        safeFetch(nameStartsWith)
    }

    // função que fará a busca dos personagens e receberá o estado da busca sendo suceso ou erro
    // e notificara a view
    private suspend fun safeFetch(nameStartsWith: String) {
        _searchCharacter.value = ResourceState.Loading()
        try {
            val response = getCharacterSearchUseCase(nameStartsWith)
            _searchCharacter.value = response
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _searchCharacter.value = ResourceState.Error("Erro na rede")
                else -> _searchCharacter.value = ResourceState.Error("Erro na conversão")
            }
        }
    }

}