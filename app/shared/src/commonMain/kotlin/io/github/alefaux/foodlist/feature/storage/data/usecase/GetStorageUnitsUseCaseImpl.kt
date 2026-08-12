package io.github.alefaux.foodlist.feature.storage.data.usecase

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit

class GetStorageUnitsUseCaseImpl(
    private val repository: StorageRepository
) : GetStorageUnitsUseCase {
    override suspend fun invoke(): List<StorageUnit> =
        repository.getStorageUnits()
}
