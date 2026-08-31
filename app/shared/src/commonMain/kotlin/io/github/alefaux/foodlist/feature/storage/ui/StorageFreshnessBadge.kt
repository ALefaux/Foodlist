package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.storage_freshness_all_fresh
import foodlist.app.shared.generated.resources.storage_freshness_expiring
import org.jetbrains.compose.resources.stringResource

@Composable
fun StorageFreshnessBadge(
    expiringCount: Int,
    modifier: Modifier = Modifier
) {
    val isExpiring = expiringCount > 0
    val contentColor = if (isExpiring) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = modifier
            .background(
                color = if (isExpiring) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHighest
                },
                shape = RoundedCornerShape(100)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isExpiring) Icons.Filled.Warning else Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (isExpiring) {
                stringResource(Res.string.storage_freshness_expiring, expiringCount)
            } else {
                stringResource(Res.string.storage_freshness_all_fresh)
            },
            style = MaterialTheme.typography.labelSmall,
            color = contentColor
        )
    }
}
