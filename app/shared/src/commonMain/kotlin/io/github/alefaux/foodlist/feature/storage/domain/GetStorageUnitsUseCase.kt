package io.github.alefaux.foodlist.feature.storage.domain

interface GetStorageUnitsUseCase {
    suspend operator fun invoke(): List<StorageUnit>
}
