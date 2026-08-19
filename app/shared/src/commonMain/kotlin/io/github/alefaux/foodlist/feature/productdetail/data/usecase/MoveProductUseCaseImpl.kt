package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.MoveProductUseCase

class MoveProductUseCaseImpl(
    private val repository: ProductDetailRepository
) : MoveProductUseCase {
    override suspend fun invoke(productId: Int, storageId: Long) = repository.moveProduct(productId, storageId)
}
