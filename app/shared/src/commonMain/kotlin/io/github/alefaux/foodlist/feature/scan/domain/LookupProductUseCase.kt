package io.github.alefaux.foodlist.feature.scan.domain

interface LookupProductUseCase {
    suspend operator fun invoke(ean: String): ScannedProduct?
}
