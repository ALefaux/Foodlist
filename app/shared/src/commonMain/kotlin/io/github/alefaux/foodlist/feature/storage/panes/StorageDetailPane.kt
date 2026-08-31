package io.github.alefaux.foodlist.feature.storage.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.storage_detail_empty_category
import foodlist.app.shared.generated.resources.storage_detail_empty_no_items
import io.github.alefaux.foodlist.feature.storage.presentation.model.StorageDetailUiState
import io.github.alefaux.foodlist.feature.storage.ui.CategoryFilterChip
import io.github.alefaux.foodlist.feature.storage.ui.StorageDetailHeader
import io.github.alefaux.foodlist.feature.storage.ui.StorageProductRow
import org.jetbrains.compose.resources.stringResource

@Composable
fun StorageDetailPane(
    state: StorageDetailUiState,
    onCategorySelected: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StorageDetailHeader(
                storageName = state.storageName,
                totalCount = state.totalCount,
                expiringCount = state.expiringCount
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.categories) { category ->
                    CategoryFilterChip(
                        label = category,
                        selected = category == state.selectedCategory,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }

        if (state.products.isEmpty()) {
            item {
                Text(
                    text = if (state.totalCount == 0) {
                        stringResource(Res.string.storage_detail_empty_no_items)
                    } else {
                        stringResource(Res.string.storage_detail_empty_category)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(state.products, key = { it.id }) { product ->
                StorageProductRow(product = product, onClick = onProductClick)
            }
        }
    }
}
