package io.github.alefaux.foodlist.feature.shopping.domain

interface GetShoppingItemDetailUseCase {
    suspend operator fun invoke(itemId: Long): ShoppingItemDetail?
}
