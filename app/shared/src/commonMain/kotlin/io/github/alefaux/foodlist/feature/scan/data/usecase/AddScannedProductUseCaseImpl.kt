package io.github.alefaux.foodlist.feature.scan.data.usecase

import io.github.alefaux.foodlist.feature.scan.data.repository.ScanRepository
import io.github.alefaux.foodlist.feature.scan.domain.AddScannedProductUseCase
import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct

class AddScannedProductUseCaseImpl(
    private val repository: ScanRepository
) : AddScannedProductUseCase {
    override suspend fun invoke(product: ScannedProduct): Long = repository.saveProduct(product)
}
