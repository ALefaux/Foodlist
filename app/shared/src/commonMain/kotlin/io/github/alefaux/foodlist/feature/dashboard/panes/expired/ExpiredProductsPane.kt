package io.github.alefaux.foodlist.feature.dashboard.panes.expired

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.dashboard_no_expired_products
import io.github.alefaux.foodlist.feature.dashboard.modelui.DiscardedProducts
import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductCard
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductCountBadge
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductTitle
import io.github.alefaux.foodlist.feature.dashboard.ui.stats.MonthlyStatCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpiredProductsPane(
    discardedProducts: DiscardedProducts,
    expiredProducts: ImmutableList<ExpiredProductUi>,
    expiredProductsCount: Int,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExpiredProductTitle()
            if (expiredProductsCount > 0) {
                ExpiredProductCountBadge(
                    count = expiredProductsCount
                )
            }
        }

        if (expiredProductsCount > 0) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                expiredProducts.forEach { product ->
                    ExpiredProductCard(
                        expiredTime = product.expiredSince,
                        productIcon = Icons.Default.WaterDrop,
                        productName = product.name,
                        stockPlace = product.stockageName,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.dashboard_no_expired_products))
            }
        }

        when (discardedProducts) {
            is DiscardedProducts.Positive -> MonthlyStatCard.Positive(
                modifier = Modifier.fillMaxWidth(),
                discardedProductsCount = discardedProducts.discardedProductsCount,
                trendPercent = discardedProducts.trendPercent
            )

            is DiscardedProducts.Negative -> MonthlyStatCard.Negative(
                modifier = Modifier.fillMaxWidth(),
                discardedProductsCount = discardedProducts.discardedProductsCount,
                trendPercent = discardedProducts.trendPercent
            )
        }
    }
}

@Preview
@Composable
fun ExpiredProductsPanePreview() {
    MaterialTheme {
        ExpiredProductsPane(
            discardedProducts = DiscardedProducts.Positive(
                discardedProductsCount = 10,
                trendPercent = 10
            ),
            expiredProducts = persistentListOf(),
            expiredProductsCount = 3,
            onProductClick = {}
        )
    }
}