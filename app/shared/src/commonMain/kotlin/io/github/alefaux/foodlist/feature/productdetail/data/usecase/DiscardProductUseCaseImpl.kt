package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.DiscardProductUseCase

class DiscardProductUseCaseImpl(
    private val repository: ProductDetailRepository
) : DiscardProductUseCase {
    override suspend fun invoke(productId: Int) = repository.discardProduct(productId)
}
