package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem

class GetShoppingItemsUseCaseImpl(
    private val repository: ShoppingRepository
) : GetShoppingItemsUseCase {
    override suspend fun invoke(): List<ShoppingItem> = repository.getItems()
}
