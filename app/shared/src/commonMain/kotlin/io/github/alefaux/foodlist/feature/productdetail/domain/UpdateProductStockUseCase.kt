package io.github.alefaux.foodlist.feature.productdetail.domain

interface UpdateProductStockUseCase {
    suspend operator fun invoke(productId: Int, stock: Int)
}
