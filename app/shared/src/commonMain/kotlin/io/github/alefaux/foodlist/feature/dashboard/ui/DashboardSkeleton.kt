package io.github.alefaux.foodlist.feature.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.ShimmerBlock
import io.github.alefaux.foodlist.core.component.rememberShimmerBrush
import io.github.alefaux.foodlist.feature.dashboard.ui.expired.ExpiredProductTitle

/** Placeholder for the expired products list and the monthly stats card while they load. */
@Composable
fun DashboardSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExpiredProductTitle()

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(2) {
                ShimmerBlock(
                    brush = brush,
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = CardDefaults.shape
                )
            }
        }

        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(140.dp),
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
@Preview
private fun DashboardSkeletonPreview() {
    MaterialTheme {
        DashboardSkeleton()
    }
}
