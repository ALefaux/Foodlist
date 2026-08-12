package io.github.alefaux.foodlist.feature.storage.data.usecase

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.domain.DeleteStorageUnitUseCase

class DeleteStorageUnitUseCaseImpl(
    private val repository: StorageRepository
) : DeleteStorageUnitUseCase {
    override suspend fun invoke(storageId: Long) {
        repository.deleteStorageUnit(storageId)
    }
}
