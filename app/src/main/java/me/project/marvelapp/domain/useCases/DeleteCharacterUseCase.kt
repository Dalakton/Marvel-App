package me.project.marvelapp.domain.useCases

import me.project.marvelapp.domain.model.character.CharacterModel

interface DeleteCharacterUseCase {

    suspend operator fun invoke(characterModel: CharacterModel)
}