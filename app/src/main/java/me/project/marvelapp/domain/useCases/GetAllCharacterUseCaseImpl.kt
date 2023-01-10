package me.project.marvelapp.domain.useCases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.presentation.state.ResourceState

class GetAllCharacterUseCaseImpl(private val repository: MarvelRepository) :
    GetAllCharacterUseCase {
    override suspend fun invoke(): Flow<List<CharacterModel>> {
        return repository.getAll()
    }
}