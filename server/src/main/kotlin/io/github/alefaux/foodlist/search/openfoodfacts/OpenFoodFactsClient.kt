package io.github.alefaux.foodlist.search.openfoodfacts

import io.github.alefaux.foodlist.search.dto.SearchResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.slf4j.LoggerFactory

class OpenFoodFactsClient(
    private val httpClient: HttpClient
) {
    suspend fun search(query: String): List<SearchResultDto> = try {
        httpClient.get("$BASE_URL/search") {
            parameter("q", query)
            parameter("fields", "code,product_name,quantity,brands,categories_tags")
            parameter("page_size", PAGE_SIZE)
        }.body<OpenFoodFactsSearchResponseDto>()
            .hits
            .filter { !it.productName.isNullOrBlank() }
            .map { it.toSearchResultDto() }
    } catch (e: Exception) {
        logger.warn("OpenFoodFacts search failed for query '$query'", e)
        emptyList()
    }

    private fun OpenFoodFactsProductDto.toSearchResultDto() = SearchResultDto(
        ean = code,
        name = productName.orEmpty(),
        quantity = quantity,
        brand = brands.firstOrNull(),
        category = categories.firstOrNull { it.startsWith("fr:") }?.removePrefix("fr:")
    )

    private companion object {
        const val BASE_URL = "https://search.openfoodfacts.org"
        const val PAGE_SIZE = 20
        val logger = LoggerFactory.getLogger(OpenFoodFactsClient::class.java)
    }
}
