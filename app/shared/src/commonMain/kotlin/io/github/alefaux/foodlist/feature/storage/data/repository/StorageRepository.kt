package io.github.alefaux.foodlist.feature.storage.data.repository

import io.github.alefaux.foodlist.feature.storage.domain.StorageDetail
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit

interface StorageRepository {
    suspend fun getStorageUnits(): List<StorageUnit>
    suspend fun addStorageUnit(name: String)
    suspend fun getStorageDetail(storageId: Long): StorageDetail?
    suspend fun deleteStorageUnit(storageId: Long)
    suspend fun restoreDiscardedProduct(productId: Int)
}
