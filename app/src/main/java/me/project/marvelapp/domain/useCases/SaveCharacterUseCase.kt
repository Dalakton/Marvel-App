package me.project.marvelapp.domain.useCases

import me.project.marvelapp.domain.model.character.CharacterModel

interface SaveCharacterUseCase {

    suspend operator fun invoke(charaterModel: CharacterModel) : Long

}