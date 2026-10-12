package io.github.alefaux.foodlist.feature.shopping.domain

interface DeleteShoppingItemUseCase {
    suspend operator fun invoke(itemId: Long)
}
