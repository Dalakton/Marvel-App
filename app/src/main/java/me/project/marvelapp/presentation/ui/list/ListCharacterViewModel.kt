package me.project.marvelapp.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.project.marvelapp.domain.model.character.CharacterModelResponse
import me.project.marvelapp.domain.useCases.GetListCharacterUseCase
import me.project.marvelapp.presentation.state.ResourceState
import java.io.IOException

class ListCharacterViewModel(private val getListCharacterUseCase: GetListCharacterUseCase) :
    ViewModel() {

    private val _list =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<CharacterModelResponse>> = _list

    init {
        getCharacters()
    }

    private fun getCharacters() = viewModelScope.launch {
        getResponse()

    }

    // função que pega a resposta da api já tratada e notifica o estado da resposta par view
    private suspend fun getResponse() {
        _list.value = ResourceState.Loading()
        try {
            val response = getListCharacterUseCase()
            _list.value = response
        } catch (t: Throwable) {
            when (t) {
                // Erros genericos que podem acontecer.
                is IOException -> _list.value =
                    ResourceState.Error("Erro de conecão com a internet")
                else -> _list.value = ResourceState.Error("Falha na conexão de dados")
            }
        }
    }

}