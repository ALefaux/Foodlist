package io.github.alefaux.foodlist.feature.productdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.ShimmerBlock
import io.github.alefaux.foodlist.core.component.rememberShimmerBrush

/** Placeholder for the product detail content while it loads. */
@Composable
fun ProductDetailSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().aspectRatio(1.6f),
            shape = RoundedCornerShape(20.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ShimmerBlock(brush = brush, modifier = Modifier.fillMaxWidth(0.6f).height(32.dp))
            ShimmerBlock(brush = brush, modifier = Modifier.fillMaxWidth(0.4f).height(20.dp))
        }

        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(88.dp),
            shape = CardDefaults.shape
        )

        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(96.dp),
            shape = RoundedCornerShape(16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            repeat(2) {
                ShimmerBlock(
                    brush = brush,
                    modifier = Modifier.weight(1f).height(88.dp),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProductDetailSkeletonPreview() {
    MaterialTheme {
        ProductDetailSkeleton()
    }
}
