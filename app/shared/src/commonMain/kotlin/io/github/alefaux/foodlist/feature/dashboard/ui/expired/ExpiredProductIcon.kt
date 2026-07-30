package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpiredProductIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier.background(
            color = Color(
                red = 249,
                green = 219,
                blue = 214
            ),
            shape = RoundedCornerShape(8.dp)
        ).fillMaxHeight()
            .aspectRatio(1f)
            .padding(8.dp),
        contentDescription = null,
        imageVector = icon,
        tint = Color(
            red = 134,
            green = 25,
            blue = 21
        )
    )
}

@Composable
@Preview
private fun ExpiredProductIconPreview() {
    MaterialTheme {
        ExpiredProductIcon(
            icon = Icons.Default.WaterDrop
        )
    }
}