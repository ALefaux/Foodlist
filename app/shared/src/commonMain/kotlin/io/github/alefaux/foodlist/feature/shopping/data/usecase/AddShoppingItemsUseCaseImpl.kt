package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.AddShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem

class AddShoppingItemsUseCaseImpl(
    private val repository: ShoppingRepository
) : AddShoppingItemsUseCase {
    override suspend fun invoke(items: List<NewShoppingItem>) = repository.addItems(items)
}
