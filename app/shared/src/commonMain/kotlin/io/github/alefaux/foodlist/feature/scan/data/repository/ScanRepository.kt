package io.github.alefaux.foodlist.feature.scan.data.repository

import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct

interface ScanRepository {
    suspend fun lookupProduct(ean: String): ScannedProduct?
    /** Persists the scanned product and returns the id of the storage unit it was added to. */
    suspend fun saveProduct(product: ScannedProduct): Long
}
