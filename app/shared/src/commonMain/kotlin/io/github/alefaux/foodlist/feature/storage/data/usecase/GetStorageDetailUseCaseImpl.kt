package io.github.alefaux.foodlist.feature.storage.data.usecase

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageDetailUseCase
import io.github.alefaux.foodlist.feature.storage.domain.StorageDetail

class GetStorageDetailUseCaseImpl(
    private val repository: StorageRepository
) : GetStorageDetailUseCase {
    override suspend fun invoke(storageId: Long): StorageDetail? =
        repository.getStorageDetail(storageId)
}
