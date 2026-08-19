package io.github.alefaux.foodlist.feature.productdetail.domain

interface GetProductDetailUseCase {
    suspend operator fun invoke(productId: Int): ProductDetail?
}
