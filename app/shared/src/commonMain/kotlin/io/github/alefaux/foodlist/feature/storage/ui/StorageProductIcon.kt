package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

/** Picks an icon from the product category, in English or French. */
fun productCategoryIcon(category: String): ImageVector = when {
    category.containsAny("dairy", "laitier", "crèmerie") -> Icons.Filled.LocalDrink
    category.containsAny("produce", "légume", "fruit") -> Icons.Filled.Restaurant
    category.containsAny("meat", "viande") -> Icons.Filled.LocalDining
    else -> Icons.Filled.Inventory2
}
