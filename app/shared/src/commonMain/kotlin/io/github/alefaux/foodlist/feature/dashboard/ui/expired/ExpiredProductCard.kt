package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpiredProductCard(
    expiredTime: String,
    productIcon: ImageVector,
    productName: String,
    stockPlace: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
                .height(
                    intrinsicSize = IntrinsicSize.Max
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ExpiredProductIcon(
                icon = productIcon
            )
            ExpiredProductNameAndStock(
                modifier = Modifier.weight(1f),
                name = productName,
                stock = stockPlace
            )
            ExpiredProductStateAndTime(
                expiredTime = expiredTime
            )
        }
    }
}

@Composable
@Preview
private fun ExpiredProductCardPreview() {
    MaterialTheme {
        ExpiredProductCard(
            expiredTime = "2 days",
            productIcon = Icons.Default.WaterDrop,
            productName = "Milk",
            stockPlace = "Refrigerator",
            onClick = {}
        )
    }
}