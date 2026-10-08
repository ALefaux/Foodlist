package io.github.alefaux.foodlist.feature.search.data.usecase

import io.github.alefaux.foodlist.feature.search.data.repository.SearchRepository
import io.github.alefaux.foodlist.feature.search.domain.SearchProductsUseCase

class SearchProductsUseCaseImpl(
    private val searchRepository: SearchRepository
): SearchProductsUseCase {
    override suspend fun invoke(query: String): List<String> =
        searchRepository.search(query)
}