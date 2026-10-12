package io.github.alefaux.foodlist.feature.shopping.domain

interface UpdateShoppingItemUseCase {
    suspend operator fun invoke(itemId: Long, draft: ShoppingItemDraft)
}
