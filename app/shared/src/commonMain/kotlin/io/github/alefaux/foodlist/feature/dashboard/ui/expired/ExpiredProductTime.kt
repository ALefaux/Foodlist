package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpiredProductTime(
    expiredTime: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        text = expiredTime
    )
}