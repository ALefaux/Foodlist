package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.DeleteShoppingItemUseCase

class DeleteShoppingItemUseCaseImpl(
    private val repository: ShoppingRepository
) : DeleteShoppingItemUseCase {
    override suspend fun invoke(itemId: Long) = repository.deleteItem(itemId)
}
