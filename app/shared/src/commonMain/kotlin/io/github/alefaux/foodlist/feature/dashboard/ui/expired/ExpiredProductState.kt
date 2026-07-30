package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpiredProductState(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onErrorContainer,
        style = MaterialTheme.typography.labelMedium,
        text = "Expired"
    )
}