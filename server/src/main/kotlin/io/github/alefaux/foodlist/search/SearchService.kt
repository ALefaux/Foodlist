package io.github.alefaux.foodlist.search

import io.github.alefaux.foodlist.database.ProductRepository
import io.github.alefaux.foodlist.model.Product
import io.github.alefaux.foodlist.search.dto.SearchResultDto
import io.github.alefaux.foodlist.search.openfoodfacts.OpenFoodFactsClient

class SearchService(
    private val productRepository: ProductRepository,
    private val openFoodFactsClient: OpenFoodFactsClient
) {
    suspend fun search(query: String?): List<SearchResultDto> {
        if (query.isNullOrBlank()) {
            throw InvalidQueryException()
        }

        val openFoodFactsResults = openFoodFactsClient.search(query)
        if (openFoodFactsResults.isNotEmpty()) {
            return openFoodFactsResults
        }

        return productRepository.search(query).map { it.toSearchResultDto() }
    }

    private fun Product.toSearchResultDto() = SearchResultDto(
        ean = ean,
        name = name,
        quantity = quantity,
        brand = null,
        category = category
    )
}