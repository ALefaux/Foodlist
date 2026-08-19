package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.DeleteProductUseCase

class DeleteProductUseCaseImpl(
    private val repository: ProductDetailRepository
) : DeleteProductUseCase {
    override suspend fun invoke(productId: Int) = repository.deleteProduct(productId)
}
