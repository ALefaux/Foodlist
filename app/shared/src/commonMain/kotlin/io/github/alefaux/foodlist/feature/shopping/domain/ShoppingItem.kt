package io.github.alefaux.foodlist.feature.shopping.domain

import kotlin.time.Instant

data class ShoppingItem(
    val id: Long,
    val name: String,
    val quantity: String,
    val count: Int,
    val priority: ShoppingPriority,
    val purchasedAt: Instant?
) {
    val isPurchased: Boolean get() = purchasedAt != null
}

data class NewShoppingItem(
    val name: String,
    val quantity: String,
    /** Recipe the item is needed for, when it comes from a suggestion. */
    val recipeId: Long? = null
)

/** Order of the "To Buy" list: urgent items first. */
enum class ShoppingPriority {
    URGENT,
    STANDARD,
    NICE_TO_HAVE
}
