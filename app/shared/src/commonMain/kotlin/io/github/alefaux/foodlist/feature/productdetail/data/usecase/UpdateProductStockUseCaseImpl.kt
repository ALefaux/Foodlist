package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductStockUseCase

class UpdateProductStockUseCaseImpl(
    private val repository: ProductDetailRepository
) : UpdateProductStockUseCase {
    override suspend fun invoke(productId: Int, stock: Int) = repository.updateStock(productId, stock)
}
