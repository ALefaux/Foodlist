package io.github.alefaux.foodlist.feature.storage.data.usecase

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.domain.RestoreDiscardedProductUseCase

class RestoreDiscardedProductUseCaseImpl(
    private val repository: StorageRepository
) : RestoreDiscardedProductUseCase {
    override suspend fun invoke(productId: Int) = repository.restoreDiscardedProduct(productId)
}
