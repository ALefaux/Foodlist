package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.feature.storage.modelui.StorageUnitUi

@Composable
fun StorageUnitCard(
    storageUnit: StorageUnitUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Box {
            Icon(
                imageVector = storageUnitIcon(storageUnit.name),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(56.dp)
                    .alpha(0.25f)
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        Icon(
                            imageVector = storageUnitIcon(storageUnit.name),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    StorageFreshnessBadge(expiringCount = storageUnit.expiringCount)
                }

                Column {
                    Text(
                        text = storageUnit.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "${storageUnit.productCount} Products total",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { (storageUnit.productCount / MAX_DISPLAYED_CAPACITY).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(100))
                )
            }
        }
    }
}

private const val MAX_DISPLAYED_CAPACITY = 50f
