package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.ShimmerBlock
import io.github.alefaux.foodlist.core.component.rememberShimmerBrush

/** Placeholder for the storage unit cards while they load. */
@Composable
fun StorageUnitsSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) {
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.fillMaxWidth().height(170.dp),
                shape = CardDefaults.shape
            )
        }
    }
}

/** Placeholder for the storage detail header, category chips and product rows while they load. */
@Composable
fun StorageDetailSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.size(96.dp),
                shape = RoundedCornerShape(20.dp)
            )
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.padding(top = 16.dp).width(160.dp).height(28.dp)
            )
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.padding(top = 8.dp).width(120.dp).height(28.dp),
                shape = RoundedCornerShape(100)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) {
                ShimmerBlock(
                    brush = brush,
                    modifier = Modifier.width(72.dp).height(32.dp),
                    shape = RoundedCornerShape(100)
                )
            }
        }

        repeat(4) {
            StorageProductRowSkeleton(brush = brush)
        }
    }
}

@Composable
private fun StorageProductRowSkeleton(brush: Brush) {
    ShimmerBlock(
        brush = brush,
        modifier = Modifier.fillMaxWidth().height(88.dp),
        shape = CardDefaults.shape
    )
}

@Composable
@Preview
private fun StorageUnitsSkeletonPreview() {
    MaterialTheme {
        StorageUnitsSkeleton()
    }
}

@Composable
@Preview
private fun StorageDetailSkeletonPreview() {
    MaterialTheme {
        StorageDetailSkeleton()
    }
}
