package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpiredProductCountBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onErrorContainer,
                shape = RoundedCornerShape(100)
            )
            .padding(horizontal = 8.dp),
        color = Color.White,
        text = "$count Alerts"
    )
}

@Composable
@Preview
private fun ExpiredProductCountBadgePreview() {
    MaterialTheme {
        ExpiredProductCountBadge(
            count = 3
        )
    }
}