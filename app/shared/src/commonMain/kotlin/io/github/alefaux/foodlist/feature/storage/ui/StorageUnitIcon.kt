package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.ui.graphics.vector.ImageVector

fun storageUnitIcon(name: String): ImageVector = when {
    name.contains("fridge", ignoreCase = true) ||
        name.contains("refrigerator", ignoreCase = true) -> Icons.Filled.Kitchen

    name.contains("freezer", ignoreCase = true) -> Icons.Filled.AcUnit
    name.contains("garage", ignoreCase = true) -> Icons.Filled.Garage
    else -> Icons.Filled.Inventory2
}
