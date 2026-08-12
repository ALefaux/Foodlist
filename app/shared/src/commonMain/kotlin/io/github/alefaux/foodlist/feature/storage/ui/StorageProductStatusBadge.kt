package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.model.ProductFreshness

@Composable
fun StorageProductStatusBadge(
    freshness: ProductFreshness,
    label: String,
    modifier: Modifier = Modifier
) {
    val containerColor = when (freshness) {
        ProductFreshness.EXPIRED -> MaterialTheme.colorScheme.error
        ProductFreshness.EXPIRING_SOON -> MaterialTheme.colorScheme.secondary
        ProductFreshness.FRESH -> MaterialTheme.colorScheme.primaryContainer
    }
    val contentColor = when (freshness) {
        ProductFreshness.EXPIRED -> MaterialTheme.colorScheme.onError
        ProductFreshness.EXPIRING_SOON -> MaterialTheme.colorScheme.onSecondary
        ProductFreshness.FRESH -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    Text(
        modifier = modifier
            .background(color = containerColor, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = contentColor
    )
}
