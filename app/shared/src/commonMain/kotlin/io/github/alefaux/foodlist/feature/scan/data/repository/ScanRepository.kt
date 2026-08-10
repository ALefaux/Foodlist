package io.github.alefaux.foodlist.feature.scan.data.repository

import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct

interface ScanRepository {
    suspend fun lookupProduct(ean: String): ScannedProduct?
    suspend fun saveProduct(product: ScannedProduct)
}
