package io.github.alefaux.foodlist.search.openfoodfacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsSearchResponseDto(
    val hits: List<OpenFoodFactsProductDto> = emptyList()
)

@Serializable
data class OpenFoodFactsProductDto(
    val code: String? = null,
    @SerialName("product_name") val productName: String? = null,
    val quantity: String? = null,
    val brands: List<String> = emptyList(),
    @SerialName("categories_tags") val categories: List<String> = emptyList()
)
