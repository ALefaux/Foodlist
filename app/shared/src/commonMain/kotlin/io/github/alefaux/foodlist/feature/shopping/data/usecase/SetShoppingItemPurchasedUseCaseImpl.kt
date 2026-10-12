package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.SetShoppingItemPurchasedUseCase

class SetShoppingItemPurchasedUseCaseImpl(
    private val repository: ShoppingRepository
) : SetShoppingItemPurchasedUseCase {
    override suspend fun invoke(itemId: Long, isPurchased: Boolean) = repository.setPurchased(itemId, isPurchased)
}
