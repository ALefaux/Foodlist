package io.github.alefaux.foodlist.feature.dashboard.presentation.model

import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class DashboardUiState(
    val expiredProducts: ImmutableList<ExpiredProductUi> = persistentListOf(),
    val expiredProductsCount: Int = 0
)
