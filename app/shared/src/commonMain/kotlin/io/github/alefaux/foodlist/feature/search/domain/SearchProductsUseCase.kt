package io.github.alefaux.foodlist.feature.search.domain

interface SearchProductsUseCase {
    suspend operator fun invoke(query: String): List<String>
}