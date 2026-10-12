package io.github.alefaux.foodlist.feature.shopping.data.usecase

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemDetailUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDetail

class GetShoppingItemDetailUseCaseImpl(
    private val repository: ShoppingRepository
) : GetShoppingItemDetailUseCase {
    override suspend fun invoke(itemId: Long): ShoppingItemDetail? = repository.getItemDetail(itemId)
}
