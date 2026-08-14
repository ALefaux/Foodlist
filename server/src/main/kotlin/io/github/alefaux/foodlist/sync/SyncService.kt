package io.github.alefaux.foodlist.sync

import io.github.alefaux.foodlist.database.ProductSyncRecord
import io.github.alefaux.foodlist.database.ProductSyncRepository
import io.github.alefaux.foodlist.database.StorageUnitRepository
import io.github.alefaux.foodlist.sync.dto.SyncRequest
import io.github.alefaux.foodlist.sync.dto.SyncResponse

class SyncService(
    private val storageUnitRepository: StorageUnitRepository,
    private val productSyncRepository: ProductSyncRepository
) {
    fun sync(userId: Long, request: SyncRequest): SyncResponse {
        val storageIdMap = storageUnitRepository.replaceAllForUser(
            userId = userId,
            units = request.storageUnits.map { it.localId to it.name }
        )

        val products = request.products.map { product ->
            ProductSyncRecord(
                name = product.name,
                expirationDate = product.expirationDate,
                ean = product.ean,
                discardedDate = product.discardedDate,
                storageUnitId = product.localStorageId?.let { storageIdMap[it] },
                quantity = product.quantity,
                category = product.category
            )
        }
        productSyncRepository.replaceAllForUser(userId, products)

        return SyncResponse(
            syncedStorageUnits = storageIdMap.size,
            syncedProducts = products.size
        )
    }
}
