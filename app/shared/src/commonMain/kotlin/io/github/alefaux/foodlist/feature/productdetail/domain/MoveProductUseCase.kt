package io.github.alefaux.foodlist.feature.productdetail.domain

interface MoveProductUseCase {
    suspend operator fun invoke(productId: Int, storageId: Long)
}
