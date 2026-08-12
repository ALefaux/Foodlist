package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

fun productCategoryIcon(category: String): ImageVector = when {
    category.contains("dairy", ignoreCase = true) -> Icons.Filled.LocalDrink
    category.contains("produce", ignoreCase = true) -> Icons.Filled.Restaurant
    category.contains("meat", ignoreCase = true) -> Icons.Filled.LocalDining
    else -> Icons.Filled.Inventory2
}
