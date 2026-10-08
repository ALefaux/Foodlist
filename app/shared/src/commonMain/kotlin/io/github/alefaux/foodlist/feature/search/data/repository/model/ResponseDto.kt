package io.github.alefaux.foodlist.feature.search.data.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val count: Int,
    val page: Int,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("page_size")
    val pageSize: Int,
    val products: List<SearchProductDto>
)