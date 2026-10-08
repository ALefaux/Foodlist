package io.github.alefaux.foodlist.feature.storage.data.repository

import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
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
        return StorageDetail(
            id = storage.id,
            name = storage.name,
            products = productDao.getByStorageId(storageId).map { it.toStorageProduct() },
            discardedProducts = productDao.getDiscardedByStorageId(storageId).map { it.toStorageProduct() }
        )
    }

    override suspend fun deleteStorageUnit(storageId: Long) {
        productDao.clearStorageReference(storageId)
        storageDao.deleteById(storageId)
    }

    override suspend fun restoreDiscardedProduct(productId: Int) {
        productDao.restoreDiscarded(productId)
    }

    private fun ProductEntity.toStorageProduct() = StorageProduct(
        id = id,
        name = name,
        quantity = quantity,
        category = category,
        expirationDate = expirationDate?.toLocalDate(),
        discardedDate = discardedDate?.toLocalDate()
    )
}
