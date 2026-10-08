package io.github.alefaux.foodlist.feature.search.data.service

import io.github.alefaux.foodlist.feature.search.data.repository.model.ResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SearchService(
    private val httpClient: HttpClient
) {
    suspend fun search(query: String): ResponseDto =
        httpClient
            .get("https://world.openfoodfacts.org/cgi/search?search_terms=$query&fields=product_name,id,code")
            .body<ResponseDto>()

    // "https://world.openfoodfacts.org/api/v3/search?product_name=$query&fields=product_name,id,code"
    // "https://world.openfoodfacts.org/api/v3/search?search_terms=$query&fields=product_name,id,code"

    // search_terms
}