package io.github.alefaux.foodlist.feature.dashboard.presentation.model

import io.github.alefaux.foodlist.feature.dashboard.modelui.DiscardedProducts
import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class DashboardUiState(
    val expiredProducts: ImmutableList<ExpiredProductUi> = persistentListOf(),
    val expiredProductsCount: Int = 0,
    val discardedProducts: DiscardedProducts = DiscardedProducts.Positive(
        discardedProductsCount = 0,
        trendPercent = 0
    ),
    val isDebug: Boolean = false,
    val isServerUp: Boolean = false
)
