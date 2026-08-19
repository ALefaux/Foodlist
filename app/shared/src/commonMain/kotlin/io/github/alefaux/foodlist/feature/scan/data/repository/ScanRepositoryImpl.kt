package io.github.alefaux.foodlist.feature.scan.data.repository

import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.feature.scan.data.remote.OpenFoodFactsResponseDto
import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ScanRepositoryImpl(
    private val httpClient: HttpClient,
    private val productDao: ProductDao,
    private val storageDao: StorageDao
) : ScanRepository {

    override suspend fun lookupProduct(ean: String): ScannedProduct? {
        val response = httpClient.get("$OPEN_FOOD_FACTS_BASE_URL_V2/v2/product/$ean") {
            parameter("cc", "fr")
            parameter("lc", "fr")
            parameter("fields", "product_name,quantity,brands")
        }.body<OpenFoodFactsResponseDto>()

        val product = response.product ?: return null
        val name = product.productName
        if (response.status != 1 || name.isNullOrBlank()) return null

        return ScannedProduct(
            ean = ean,
            name = name,
            quantity = product.quantity.orEmpty(),
            brand = product.brands
        )
    }

    override suspend fun saveProduct(product: ScannedProduct) {
        productDao.insert(
            ProductEntity(
                name = product.name,
                expirationDate = null,
                ean = product.ean,
                discardedDate = null,
                storageId = resolveStorageId()
            )
        )
    }

    private suspend fun resolveStorageId(): Long =
        storageDao.getLatest()?.id
            ?: storageDao.insert(StorageEntity(name = DEFAULT_STORAGE_NAME))

    companion object {
        private const val OPEN_FOOD_FACTS_BASE_URL_V2 = "https://world.openfoodfacts.org/api"
        private const val DEFAULT_STORAGE_NAME = "Storage"
    }
}
