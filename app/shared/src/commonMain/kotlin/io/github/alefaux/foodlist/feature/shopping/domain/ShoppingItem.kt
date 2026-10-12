package io.github.alefaux.foodlist.feature.shopping.domain

import kotlin.time.Instant

data class ShoppingItem(
    val id: Long,
    val name: String,
    val quantity: String,
    val purchasedAt: Instant?
) {
    val isPurchased: Boolean get() = purchasedAt != null
}

data class NewShoppingItem(
    val name: String,
    val quantity: String
)
