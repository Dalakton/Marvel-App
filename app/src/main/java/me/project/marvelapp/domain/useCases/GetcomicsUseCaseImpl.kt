package me.project.marvelapp.domain.useCases

import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.domain.model.comic.ComicModelResponse
import me.project.marvelapp.presentation.state.ResourceState
import retrofit2.Response

class GetcomicsUseCaseImpl(private val repository: MarvelRepository) : GetComicsUseCase {

    override suspend fun invoke(characterId: Int): ResourceState<ComicModelResponse> {
        val response = repository.getComics(characterId)

        if (response.isSuccessful) {
            response.body()?.let { comic ->
                return ResourceState.Sucess(comic)
            }
        }
        return ResourceState.Error(response.message())
    }
}