package io.github.alefaux.foodlist.feature.shopping.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_delete
import foodlist.app.shared.generated.resources.shopping_restore
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import org.jetbrains.compose.resources.stringResource

/**
 * An item of the list. Ticking it marks it as purchased; a purchased item shows a button
 * to put it back on the list.
 */
@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onClick: () -> Unit,
    onPurchasedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (item.isPurchased) 0.6f else 1f),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isPurchased) colorScheme.surfaceContainer else colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (item.isPurchased) 0.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isPurchased,
                onCheckedChange = onPurchasedChange
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = if (item.isPurchased) TextDecoration.LineThrough else null,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (!item.isPurchased) {
                        ShoppingPriorityBadge(priority = item.priority)
                    }
                }
                val amount = listOfNotNull(
                    item.count.takeIf { it > 1 }?.let { "$it ×" },
                    item.quantity.takeIf { it.isNotBlank() }
                ).joinToString(" ")
                if (amount.isNotBlank()) {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurfaceVariant,
                        textDecoration = if (item.isPurchased) TextDecoration.LineThrough else null
                    )
                }
            }
            if (item.isPurchased) {
                IconButton(onClick = { onPurchasedChange(false) }) {
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = stringResource(Res.string.shopping_restore),
                        tint = colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(Res.string.shopping_delete),
                    tint = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
@Preview
private fun ShoppingItemRowPreview() {
    MaterialTheme {
        ShoppingItemRow(
            item = ShoppingItem(
                id = 1,
                name = "Whole Milk",
                quantity = "1 L",
                count = 2,
                priority = ShoppingPriority.URGENT,
                purchasedAt = null
            ),
            onClick = {},
            onPurchasedChange = {},
            onDelete = {}
        )
    }
}
