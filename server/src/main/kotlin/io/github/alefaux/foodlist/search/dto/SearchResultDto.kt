package io.github.alefaux.foodlist.search.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    val ean: String?,
    val name: String,
    val quantity: String?,
    val brand: String?,
    val category: String?
)
