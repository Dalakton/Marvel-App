package me.project.marvelapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import me.project.marvelapp.domain.useCases.GetComicsUseCase
import me.project.marvelapp.domain.useCases.SaveCharacterUseCase
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response
import java.io.IOException

class DetailViewModel(
    private val getComicsUseCase: GetComicsUseCase,
    private val saveCharacterUseCase : SaveCharacterUseCase
) : ViewModel() {

    private val _details =
        MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loading())
    val detail: StateFlow<ResourceState<ComicModelResponse>> = _details

     fun fetch(characterId: Int) = viewModelScope.launch {
        safeFetch(characterId)
    }

    private suspend fun safeFetch(characterId: Int) {
        _details.value = ResourceState.Loading()
        try {
            val response = getComicsUseCase(characterId)
            _details.value = response
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _details.value = ResourceState.Error("Erro na rede")
                else -> _details.value = ResourceState.Error("Erro na conversão")
            }
        }
    }

    fun insert(charaterModel: CharacterModel) = viewModelScope.launch{
        saveCharacterUseCase.invoke(charaterModel)

    }
}