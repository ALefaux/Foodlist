package io.github.alefaux.foodlist.feature.scan.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun ScannerViewfinder(
    isActive: Boolean,
    modifier: Modifier = Modifier,
    frameColor: Color = Color(0xFFA4D393)
) {
    val transition = rememberInfiniteTransition(label = "scan-line")
    val lineProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scan-line-progress"
    )

    Canvas(modifier = modifier) {
        val cornerLength = size.minDimension * 0.12f
        val strokeWidth = 6f

        listOf(
            Offset(0f, 0f) to Offset(cornerLength, 0f),
            Offset(0f, 0f) to Offset(0f, cornerLength),
            Offset(size.width, 0f) to Offset(size.width - cornerLength, 0f),
            Offset(size.width, 0f) to Offset(size.width, cornerLength),
            Offset(0f, size.height) to Offset(cornerLength, size.height),
            Offset(0f, size.height) to Offset(0f, size.height - cornerLength),
            Offset(size.width, size.height) to Offset(size.width - cornerLength, size.height),
            Offset(size.width, size.height) to Offset(size.width, size.height - cornerLength)
        ).forEach { (start, end) ->
            drawLine(color = frameColor, start = start, end = end, strokeWidth = strokeWidth)
        }

        if (isActive) {
            val y = size.height * lineProgress
            drawLine(
                color = frameColor.copy(alpha = 0.8f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth / 2
            )
        }
    }
}
