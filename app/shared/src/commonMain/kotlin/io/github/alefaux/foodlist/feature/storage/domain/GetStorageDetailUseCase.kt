package io.github.alefaux.foodlist.feature.storage.domain

interface GetStorageDetailUseCase {
    suspend operator fun invoke(storageId: Long): StorageDetail?
}
