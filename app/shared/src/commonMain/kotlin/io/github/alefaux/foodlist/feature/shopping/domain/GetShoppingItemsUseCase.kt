package io.github.alefaux.foodlist.feature.shopping.domain

interface GetShoppingItemsUseCase {
    suspend operator fun invoke(): List<ShoppingItem>
}
