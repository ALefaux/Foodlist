package io.github.alefaux.foodlist.feature.scan.domain

interface AddScannedProductUseCase {
    /** Returns the id of the storage unit the product was added to. */
    suspend operator fun invoke(product: ScannedProduct): Long
}
