package io.github.alefaux.foodlist.feature.storage.data.usecase

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.domain.AddStorageUnitUseCase

class AddStorageUnitUseCaseImpl(
    private val repository: StorageRepository
) : AddStorageUnitUseCase {
    override suspend fun invoke(name: String) {
        repository.addStorageUnit(name)
    }
}
