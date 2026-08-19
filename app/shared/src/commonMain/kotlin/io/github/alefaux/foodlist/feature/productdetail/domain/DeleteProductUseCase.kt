package io.github.alefaux.foodlist.feature.productdetail.domain

interface DeleteProductUseCase {
    suspend operator fun invoke(productId: Int)
}
