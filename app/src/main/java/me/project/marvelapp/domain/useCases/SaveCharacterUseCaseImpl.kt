package me.project.marvelapp.domain.useCases

import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.character.CharacterModel

class SaveCharacterUseCaseImpl(private val repository: MarvelRepository) : SaveCharacterUseCase {
    override suspend fun invoke(charaterModel: CharacterModel): Long {
        return repository.insert(charaterModel)
    }
}