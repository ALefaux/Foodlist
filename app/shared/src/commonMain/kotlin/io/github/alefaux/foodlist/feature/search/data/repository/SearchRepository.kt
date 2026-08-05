package io.github.alefaux.foodlist.feature.search.data.repository

interface SearchRepository {
    suspend fun search(query: String): List<String>
}