package io.github.alefaux.foodlist.feature.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.ShimmerBlock
import io.github.alefaux.foodlist.core.component.rememberShimmerBrush

/** Placeholder for the recipe cards while they load. */
@Composable
fun RecipesSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(2) {
            ShimmerBlock(
                brush = brush,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                shape = CardDefaults.shape
            )
        }
    }
}

@Composable
@Preview
private fun RecipesSkeletonPreview() {
    MaterialTheme {
        RecipesSkeleton()
    }
}
