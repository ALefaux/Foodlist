package io.github.alefaux.foodlist.feature.storage.domain

interface RestoreDiscardedProductUseCase {
    suspend operator fun invoke(productId: Int)
}
