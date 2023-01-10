package me.project.marvelapp.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.domain.useCases.DeleteCharacterUseCase
import me.project.marvelapp.domain.useCases.GetAllCharacterUseCase
import me.project.marvelapp.presentation.state.ResourceState

class FavoriteViewModel(
    private val getAllCharacterUseCase: GetAllCharacterUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<ResourceState<List<CharacterModel>>>(ResourceState.Empty())
     val favorites: StateFlow<ResourceState<List<CharacterModel>>> = _favorites

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        getAllCharacterUseCase.invoke().collectLatest { characters ->
            if (characters.isNullOrEmpty()) {
                _favorites.value = ResourceState.Empty()
            } else {
                _favorites.value = ResourceState.Sucess(characters)
            }

        }

    }

    fun delete(characterModel: CharacterModel) = viewModelScope.launch {
        deleteCharacterUseCase.invoke(characterModel)

    }

}