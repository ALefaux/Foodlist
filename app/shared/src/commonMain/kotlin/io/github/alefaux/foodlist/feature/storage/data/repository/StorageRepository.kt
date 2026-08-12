package io.github.alefaux.foodlist.feature.storage.data.repository

import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit

interface StorageRepository {
    suspend fun getStorageUnits(): List<StorageUnit>
    suspend fun addStorageUnit(name: String)
}
