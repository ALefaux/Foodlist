package io.github.alefaux.foodlist.core.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Brush sweeping a highlight across placeholder blocks. Create it once per skeleton and pass it to
 * every [ShimmerBlock] so all blocks animate in sync.
 */
@Composable
fun rememberShimmerBrush(): Brush {
    val baseColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val highlightColor = MaterialTheme.colorScheme.surfaceContainerLowest

    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SHIMMER_DURATION_MILLIS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerProgress"
    )

    val start = -SHIMMER_WIDTH + progress * SHIMMER_TRAVEL
    return Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(start, 0f),
        end = Offset(start + SHIMMER_WIDTH, SHIMMER_WIDTH)
    )
}

@Composable
fun ShimmerBlock(
    brush: Brush,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Box(modifier = modifier.background(brush = brush, shape = shape))
}

private const val SHIMMER_DURATION_MILLIS = 1200
private const val SHIMMER_WIDTH = 400f
private const val SHIMMER_TRAVEL = 2000f

@Composable
@Preview
private fun ShimmerBlockPreview() {
    MaterialTheme {
        val brush = rememberShimmerBrush()
        Column(modifier = Modifier.padding(16.dp)) {
            ShimmerBlock(brush = brush, modifier = Modifier.fillMaxWidth().height(80.dp))
            ShimmerBlock(brush = brush, modifier = Modifier.padding(top = 8.dp).fillMaxWidth(0.6f).height(20.dp))
        }
    }
}
