package io.github.alefaux.foodlist.feature.shopping.domain

interface SetShoppingItemPurchasedUseCase {
    suspend operator fun invoke(itemId: Long, isPurchased: Boolean)
}
