package io.github.alefaux.foodlist.feature.dashboard.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.dashboard_greeting
import io.github.alefaux.foodlist.core.component.HomeHeader
import io.github.alefaux.foodlist.feature.dashboard.modelui.DiscardedProducts
import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import io.github.alefaux.foodlist.feature.dashboard.panes.expired.ExpiredProductsPane
import io.github.alefaux.foodlist.feature.dashboard.ui.menu.DashboardMenu
import io.github.alefaux.foodlist.feature.dashboard.ui.server.ServerStatusChip
import io.github.alefaux.foodlist.core.component.SearchTextField
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardPane(
    expiredProducts: ImmutableList<ExpiredProductUi>,
    expiredProductsCount: Int,
    onAddClick: () -> Unit,
    onScanClick: () -> Unit,
    onExpiredProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isDebug: Boolean = false,
    isServerUp: Boolean = false
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeHeader(
                    title = stringResource(Res.string.dashboard_greeting)
                )
                if (isDebug) {
                    ServerStatusChip(
                        isServerUp = isServerUp
                    )
                }
                SearchTextField()
            }
        }
        item {
            DashboardMenu(
                onAddClick = onAddClick,
                onScanClick = onScanClick
            )
        }
        item {
            ExpiredProductsPane(
                discardedProducts = DiscardedProducts.Positive(
                    discardedProductsCount = 10,
                    trendPercent = 10
                ),
                expiredProducts = expiredProducts,
                expiredProductsCount = expiredProductsCount,
                onProductClick = onExpiredProductClick
            )
        }
    }
}