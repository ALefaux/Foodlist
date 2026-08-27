package io.github.alefaux.foodlist.feature.scan.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsResponseDto(
    val status: Int = 0,
    val product: OpenFoodFactsProductDto? = null
)

@Serializable
data class OpenFoodFactsProductDto(
    @SerialName("product_name") val productName: String? = null,
    val quantity: String? = null,
    val brands: String? = null,
    @SerialName("categories_hierarchy") val categories: List<String>
)
