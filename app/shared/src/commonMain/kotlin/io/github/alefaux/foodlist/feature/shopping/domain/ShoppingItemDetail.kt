package io.github.alefaux.foodlist.feature.shopping.domain

data class ShoppingItemDetail(
    val id: Long,
    val isPurchased: Boolean,
    /** Recipe the item was suggested for, and its name while that recipe still exists. */
    val recipeId: Long?,
    val recipeName: String?,
    val draft: ShoppingItemDraft
)

/** The editable part of a shopping item. */
data class ShoppingItemDraft(
    val name: String,
    val quantity: String,
    val count: Int,
    val category: String,
    val storageId: Long?,
    val priority: ShoppingPriority,
    val brands: String,
    val note: String
)
