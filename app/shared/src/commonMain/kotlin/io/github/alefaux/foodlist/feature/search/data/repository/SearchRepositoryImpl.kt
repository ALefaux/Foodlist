package io.github.alefaux.foodlist.feature.search.data.repository

import io.github.alefaux.foodlist.feature.search.data.service.SearchService

class SearchRepositoryImpl(
    private val service: SearchService
) : SearchRepository {
    override suspend fun search(query: String): List<String> =
        service.search(query).products.map {
            it.productName
        }
}