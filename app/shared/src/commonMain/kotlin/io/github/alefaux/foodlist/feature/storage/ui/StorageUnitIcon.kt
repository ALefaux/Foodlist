package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.ui.graphics.vector.ImageVector

/** Picks an icon from the storage name, in English or French. */
fun storageUnitIcon(name: String): ImageVector = when {
    name.containsAny("fridge", "refrigerator", "frigo", "réfrigérateur") -> Icons.Filled.Kitchen
    name.containsAny("freezer", "congélateur", "congel") -> Icons.Filled.AcUnit
    name.containsAny("garage") -> Icons.Filled.Garage
    else -> Icons.Filled.Inventory2
}

internal fun String.containsAny(vararg keywords: String): Boolean =
    keywords.any { contains(it, ignoreCase = true) }
