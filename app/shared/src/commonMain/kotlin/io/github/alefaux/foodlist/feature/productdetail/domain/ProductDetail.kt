package io.github.alefaux.foodlist.feature.productdetail.domain

import io.github.alefaux.foodlist.core.model.ProductFreshness
import kotlinx.datetime.LocalDate

data class ProductDetail(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String,
    val stock: Int,
    val expirationDate: LocalDate?,
    val createdAt: LocalDate?,
    val storageId: Long?,
    val storageName: String?,
    val freshness: ProductFreshness,
    val statusLabel: String
)
