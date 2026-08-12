package io.github.alefaux.foodlist.feature.storage.modelui

import io.github.alefaux.foodlist.core.model.ProductFreshness

data class StorageProductUi(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String,
    val freshness: ProductFreshness,
    val statusLabel: String
)
