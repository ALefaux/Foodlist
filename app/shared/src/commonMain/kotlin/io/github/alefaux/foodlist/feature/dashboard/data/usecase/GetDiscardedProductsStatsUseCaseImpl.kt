package io.github.alefaux.foodlist.feature.dashboard.data.usecase

import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.feature.dashboard.data.repository.DashboardRepository
import io.github.alefaux.foodlist.feature.dashboard.domain.DiscardedProductsStats
import io.github.alefaux.foodlist.feature.dashboard.domain.GetDiscardedProductsStatsUseCase
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlin.time.Clock

class GetDiscardedProductsStatsUseCaseImpl(
    private val repository: DashboardRepository,
    private val clock: Clock = Clock.System
): GetDiscardedProductsStatsUseCase {
    override suspend fun invoke(): DiscardedProductsStats {
        val today = clock.now().toLocalDate()
        val currentMonthStart = LocalDate(today.year, today.month, 1)
        val previousMonthStart = currentMonthStart.minus(DatePeriod(months = 1))

        val discardedDates = repository.getProducts().mapNotNull { it.discardedDate }

        return DiscardedProductsStats(
            currentMonthCount = discardedDates.count { it >= currentMonthStart },
            previousMonthCount = discardedDates.count { it >= previousMonthStart && it < currentMonthStart }
        )
    }
}
