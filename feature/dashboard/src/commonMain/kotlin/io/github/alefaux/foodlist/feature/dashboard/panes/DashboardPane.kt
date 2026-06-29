package io.github.alefaux.foodlist.feature.dashboard.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.feature.dashboard.panes.expired.ExpiredProductsPane
import io.github.alefaux.foodlist.feature.dashboard.ui.menu.DashboardMenu
import io.github.alefaux.foodlist.feature.dashboard.ui.search.SearchTextField

@Composable
fun DashboardPane(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchTextField()
        DashboardMenu()
        ExpiredProductsPane(
            expiredProductsCount = 3
        )
    }
}