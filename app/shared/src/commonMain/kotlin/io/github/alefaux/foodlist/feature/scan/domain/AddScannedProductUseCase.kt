package io.github.alefaux.foodlist.feature.scan.domain

interface AddScannedProductUseCase {
    suspend operator fun invoke(product: ScannedProduct)
}
