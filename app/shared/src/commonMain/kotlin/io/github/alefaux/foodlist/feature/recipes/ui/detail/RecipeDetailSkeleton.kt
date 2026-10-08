package io.github.alefaux.foodlist.feature.recipes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

/** Placeholder for the recipe detail content while it loads. */
@Composable
fun RecipeDetailSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(220.dp),
            shape = RoundedCornerShape(20.dp)
        )
        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(88.dp),
            shape = CardDefaults.shape
        )
        ShimmerBlock(
            brush = brush,
            modifier = Modifier.fillMaxWidth().height(76.dp),
            shape = CardDefaults.shape
        )
        ShimmerBlock(brush = brush, modifier = Modifier.fillMaxWidth(0.5f).height(24.dp))
        repeat(3) {
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = CardDefaults.shape
            )
        }
    }
}

@Composable
@Preview
private fun RecipeDetailSkeletonPreview() {
    MaterialTheme {
        RecipeDetailSkeleton()
    }
}
