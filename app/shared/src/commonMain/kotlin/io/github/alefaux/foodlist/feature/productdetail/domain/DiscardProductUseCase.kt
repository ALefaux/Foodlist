package io.github.alefaux.foodlist.feature.productdetail.domain

interface DiscardProductUseCase {
    suspend operator fun invoke(productId: Int)
}
