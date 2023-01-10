package me.project.marvelapp.domain.useCases

import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.character.CharacterModel

class DeleteCharacterUseCaseImpl(private val repository: MarvelRepository) :
    DeleteCharacterUseCase {
    override suspend fun invoke(characterModel: CharacterModel) {
        return repository.delete(characterModel)
    }
}