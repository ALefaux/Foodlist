package io.github.alefaux.foodlist.feature.dashboard.ui.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object MonthlyStatCard {
    @Composable
    fun Positive(
        discardedProductsCount: Int,
        trendPercent: Int,
        modifier: Modifier = Modifier
    ) {
        MonthlyStatCardImpl(
            modifier = modifier,
            accentColor = Color(0xFFBFEBAC),
            backgroundColor = Color(0xFF3F6B34),
            discardedProductsCount = discardedProductsCount,
            trendIcon = Icons.AutoMirrored.Default.TrendingDown,
            trendText = "${trendPercent}% less than last month",
            waveColor = Color(0xFF5A8A4A)
        )
    }

    @Composable
    fun Negative(
        discardedProductsCount: Int,
        trendPercent: Int,
        modifier: Modifier = Modifier
    ) {
        MonthlyStatCardImpl(
            modifier = modifier,
            accentColor = Color(0xFFF6C7C1),
            backgroundColor = Color(0xFF7A342C),
            discardedProductsCount = discardedProductsCount,
            trendIcon = Icons.AutoMirrored.Default.TrendingUp,
            trendText = "${trendPercent}% more than last month",
            waveColor = Color(0xFF9C5449)
        )
    }

    @Composable
    private fun MonthlyStatCardImpl(
        accentColor: Color,
        backgroundColor: Color,
        discardedProductsCount: Int,
        trendIcon: ImageVector,
        trendText: String,
        waveColor: Color,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                TrendDecoration(
                    modifier = Modifier.matchParentSize(),
                    waveColor = waveColor,
                    lineColor = accentColor
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Discarded This Month",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = discardedProductsCount.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Products",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = trendIcon,
                            contentDescription = null,
                            tint = accentColor
                        )
                        Text(
                            text = trendText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrendDecoration(
    waveColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val wave = Path().apply {
            moveTo(0f, h * 0.78f)
            cubicTo(
                w * 0.25f, h * 0.60f,
                w * 0.35f, h * 0.95f,
                w * 0.58f, h * 0.72f
            )
            cubicTo(
                w * 0.75f, h * 0.55f,
                w * 0.85f, h * 0.68f,
                w, h * 0.45f
            )
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path = wave, color = waveColor.copy(alpha = 0.55f))

        val points = listOf(
            Offset(w * 0.42f, h * 0.72f),
            Offset(w * 0.50f, h * 0.66f),
            Offset(w * 0.58f, h * 0.78f),
            Offset(w * 0.66f, h * 0.58f),
            Offset(w * 0.74f, h * 0.62f),
            Offset(w * 0.82f, h * 0.44f),
            Offset(w * 0.90f, h * 0.48f),
            Offset(w * 0.97f, h * 0.30f)
        )
        val linePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
        }
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

@Composable
@Preview
private fun MonthlyStatCardPositivePreview() {
    MaterialTheme {
        MonthlyStatCard.Positive(
            discardedProductsCount = 7,
            trendPercent = 12
        )
    }
}

@Composable
@Preview
private fun MonthlyStatCardNegativePreview() {
    MaterialTheme {
        MonthlyStatCard.Negative(
            discardedProductsCount = 9,
            trendPercent = 18
        )
    }
}
