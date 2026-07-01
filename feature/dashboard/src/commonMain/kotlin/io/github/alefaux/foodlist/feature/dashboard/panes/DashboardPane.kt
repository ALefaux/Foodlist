package io.github.alefaux.foodlist.feature.dashboard.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.feature.dashboard.modelui.DiscardedProducts
import io.github.alefaux.foodlist.feature.dashboard.panes.expired.ExpiredProductsPane
import io.github.alefaux.foodlist.feature.dashboard.ui.menu.DashboardMenu
import io.github.alefaux.foodlist.feature.dashboard.ui.search.SearchTextField

@Composable
fun DashboardPane(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchTextField()
        DashboardMenu()
        ExpiredProductsPane(
            discardedProducts = DiscardedProducts.Positive(
                discardedProductsCount = 10,
                trendPercent = 10
            ),
            expiredProductsCount = 3
        )
    }
}