package io.github.alefaux.foodlist.feature.search.data.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchProductDto(
    val id: String,
    val code: String,
    @SerialName("product_name")
    val productName: String
)
