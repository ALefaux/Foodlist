package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDraft
import io.github.alefaux.foodlist.feature.shopping.domain.UpdateShoppingItemUseCase

class UpdateShoppingItemUseCaseImpl(
    private val repository: ShoppingRepository
) : UpdateShoppingItemUseCase {
    override suspend fun invoke(itemId: Long, draft: ShoppingItemDraft) = repository.updateItem(itemId, draft)
}
