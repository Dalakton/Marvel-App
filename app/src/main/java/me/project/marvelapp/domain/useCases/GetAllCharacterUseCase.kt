package me.project.marvelapp.domain.useCases

import kotlinx.coroutines.flow.Flow
import me.project.marvelapp.domain.model.character.CharacterModel

interface GetAllCharacterUseCase {

     suspend operator fun invoke() : Flow<List<CharacterModel>>
}