package io.github.alefaux.foodlist.feature.dashboard.data.usecase

import io.github.alefaux.foodlist.core.model.Product
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.feature.dashboard.data.repository.DashboardRepository
import io.github.alefaux.foodlist.feature.dashboard.domain.GetExpiredProductsUseCase
import kotlin.time.Clock

class GetExpiredProductsUseCaseImpl(
    private val repository: DashboardRepository
): GetExpiredProductsUseCase {
    override suspend fun invoke(): List<Product> =
        repository.getProducts()
            .filter {
                it.expirationDate != null
                        && Clock.System.now().toLocalDate() >= it.expirationDate
            }
}