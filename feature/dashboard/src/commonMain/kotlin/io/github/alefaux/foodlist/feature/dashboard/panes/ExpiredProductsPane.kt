package io.github.alefaux.foodlist.feature.dashboard.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import io.github.alefaux.foodlist.feature.dashboard.ui.ExpiredProductCountBadge
import io.github.alefaux.foodlist.feature.dashboard.ui.ExpiredProductTitle

@Composable
fun ExpiredProductsPane(
    expiredProductsCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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

        // Display 2 of expired products
    }
}

@Preview
@Composable
fun ExpiredProductsPanePreview() {
    MaterialTheme {
        ExpiredProductsPane(
            expiredProductsCount = 3
        )
    }
}