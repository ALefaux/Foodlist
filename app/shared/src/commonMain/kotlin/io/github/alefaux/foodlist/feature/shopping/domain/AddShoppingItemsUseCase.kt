package io.github.alefaux.foodlist.feature.shopping.domain

interface AddShoppingItemsUseCase {
    suspend operator fun invoke(items: List<NewShoppingItem>)
}
