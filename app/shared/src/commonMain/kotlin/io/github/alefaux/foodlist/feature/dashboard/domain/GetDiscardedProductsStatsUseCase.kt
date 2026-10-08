package io.github.alefaux.foodlist.feature.dashboard.domain

interface GetDiscardedProductsStatsUseCase {
    suspend operator fun invoke(): DiscardedProductsStats
}
