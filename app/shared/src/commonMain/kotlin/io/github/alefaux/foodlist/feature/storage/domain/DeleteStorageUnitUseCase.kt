package io.github.alefaux.foodlist.feature.storage.domain

interface DeleteStorageUnitUseCase {
    suspend operator fun invoke(storageId: Long)
}
