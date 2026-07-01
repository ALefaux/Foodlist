package io.github.alefaux.foodlist.feature.dashboard.panes.expired

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.feature.dashboard.modelui.DiscardedProducts
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductCard
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductCountBadge
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductTitle
import io.github.alefaux.foodlist.feature.dashboard.ui.stats.MonthlyStatCard

@Composable
fun ExpiredProductsPane(
    discardedProducts: DiscardedProducts,
    expiredProductsCount: Int,
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
            ExpiredProductCountBadge(
                count = expiredProductsCount
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpiredProductCard(
                expiredTime = "2 days",
                productIcon = Icons.Default.WaterDrop,
                productName = "Milk",
                stockPlace = "Refrigerator"
            )
            ExpiredProductCard(
                expiredTime = "2 days",
                productIcon = Icons.Default.WaterDrop,
                productName = "Milk",
                stockPlace = "Refrigerator"
            )
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
            expiredProductsCount = 3
        )
    }
}