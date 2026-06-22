package io.github.alefaux.foodlist.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenu(
    val icon: ImageVector,
    val label: String
) {
    data object Dashboard: BottomMenu(
        icon = Icons.Filled.Dashboard,
        label = "Dashboard"
    )
    data object Storage: BottomMenu(
        icon = Icons.Default.Inventory2,
        label = "Storage"
    )
    data object Recipes: BottomMenu(
        icon = Icons.AutoMirrored.Default.MenuBook,
        label = "Recipes"
    )
}