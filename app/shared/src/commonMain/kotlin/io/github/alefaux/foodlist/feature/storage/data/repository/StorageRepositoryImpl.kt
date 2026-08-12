package io.github.alefaux.foodlist.feature.storage.data.repository

import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.feature.storage.domain.StorageDetail
import io.github.alefaux.foodlist.feature.storage.domain.StorageProduct
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit
import kotlin.time.Clock

class StorageRepositoryImpl(
    private val storageDao: StorageDao,
    private val productDao: ProductDao
) : StorageRepository {

    override suspend fun getStorageUnits(): List<StorageUnit> {
        val today = Clock.System.now().toLocalDate()

        return storageDao.getAll().map { storage ->
            val products = productDao.getByStorageId(storage.id)

            StorageUnit(
                id = storage.id,
                name = storage.name,
                productCount = products.size,
                expiringCount = products.count {
                    it.expirationDate?.toLocalDate().toFreshness(today) != ProductFreshness.FRESH
                }
            )
        }
    }

    override suspend fun addStorageUnit(name: String) {
        storageDao.insert(StorageEntity(name = name))
    }

    override suspend fun getStorageDetail(storageId: Long): StorageDetail? {
        val storage = storageDao.getById(storageId) ?: return null
        val products = productDao.getByStorageId(storageId)

        return StorageDetail(
            id = storage.id,
            name = storage.name,
            products = products.map {
                StorageProduct(
                    id = it.id,
                    name = it.name,
                    quantity = it.quantity,
                    category = it.category,
                    expirationDate = it.expirationDate?.toLocalDate()
                )
            }
        )
    }

    override suspend fun deleteStorageUnit(storageId: Long) {
        productDao.clearStorageReference(storageId)
        storageDao.deleteById(storageId)
    }
}
