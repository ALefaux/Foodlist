package io.github.alefaux.foodlist.feature.productdetail.panes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.model.extension.toDisplayString
import io.github.alefaux.foodlist.feature.productdetail.presentation.model.ProductDetailUiState
import io.github.alefaux.foodlist.feature.productdetail.ui.ExpirationDateCard
import io.github.alefaux.foodlist.feature.productdetail.ui.ProductHeroHeader
import io.github.alefaux.foodlist.feature.productdetail.ui.ProductInfoTile
import io.github.alefaux.foodlist.feature.productdetail.ui.StockLevelCard

@Composable
fun ProductDetailPane(
    state: ProductDetailUiState,
    onIncreaseStock: () -> Unit,
    onDecreaseStock: () -> Unit,
    onEditClick: () -> Unit,
    onMoveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProductHeroHeader(
            category = state.category,
            freshness = state.freshness,
            statusLabel = state.statusLabel
        )

        Column {
            Text(
                text = state.name,
                style = MaterialTheme.typography.headlineMedium
            )
            val subtitle = listOf(state.quantity, state.category).filter { it.isNotBlank() }.joinToString(" • ")
            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        StockLevelCard(
            stock = state.stock,
            onIncrease = onIncreaseStock,
            onDecrease = onDecreaseStock
        )

        ExpirationDateCard(
            expirationDate = state.expirationDate,
            freshness = state.freshness,
            statusLabel = state.statusLabel
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductInfoTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Kitchen,
                label = "STORAGE",
                value = state.storageName ?: "Unassigned"
            )
            ProductInfoTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Category,
                label = "CATEGORY",
                value = state.category.ifBlank { "Other" }
            )
        }

        if (state.createdAt != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Added to inventory on",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = state.createdAt.toDisplayString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onEditClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            Text(
                text = "Edit Product",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onMoveClick
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                Text(
                    text = "Move to...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onDeleteClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                Text(
                    text = "Delete Item",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
