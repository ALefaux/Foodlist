package io.github.alefaux.foodlist.feature.storage.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.feature.storage.modelui.StorageUnitUi
import io.github.alefaux.foodlist.feature.storage.ui.StorageBanner
import io.github.alefaux.foodlist.feature.storage.ui.StorageHeader
import io.github.alefaux.foodlist.feature.storage.ui.StorageUnitCard
import kotlinx.collections.immutable.ImmutableList

@Composable
fun StoragePane(
    storageUnits: ImmutableList<StorageUnitUi>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StorageHeader(onAddClick = onAddClick)
        }

        if (storageUnits.isEmpty()) {
            item {
                Text(
                    text = "No storage units yet. Tap + to add your first one.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(storageUnits, key = { it.id }) { storageUnit ->
                StorageUnitCard(storageUnit = storageUnit)
            }
        }
    }
}
