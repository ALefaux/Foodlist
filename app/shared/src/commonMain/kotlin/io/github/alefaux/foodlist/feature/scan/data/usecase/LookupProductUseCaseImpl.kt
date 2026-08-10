package io.github.alefaux.foodlist.feature.scan.data.usecase

import io.github.alefaux.foodlist.feature.scan.data.repository.ScanRepository
import io.github.alefaux.foodlist.feature.scan.domain.LookupProductUseCase
import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct

class LookupProductUseCaseImpl(
    private val repository: ScanRepository
) : LookupProductUseCase {
    override suspend fun invoke(ean: String): ScannedProduct? =
        repository.lookupProduct(ean)
}
