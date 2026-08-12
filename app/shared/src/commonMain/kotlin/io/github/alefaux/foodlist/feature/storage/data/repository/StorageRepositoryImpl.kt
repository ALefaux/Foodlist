package io.github.alefaux.foodlist.feature.storage.data.repository

import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit

class StorageRepositoryImpl(
    private val storageDao: StorageDao
) : StorageRepository {

    override suspend fun getStorageUnits(): List<StorageUnit> =
        storageDao.getAll().map {
            StorageUnit(
                id = it.id,
                name = it.name,
                productCount = 0,
                expiringCount = 0
            )
        }

    override suspend fun addStorageUnit(name: String) {
        storageDao.insert(StorageEntity(name = name))
    }
}
