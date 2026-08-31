package io.github.alefaux.foodlist.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.menu_dashboard
import foodlist.app.shared.generated.resources.menu_profile
import foodlist.app.shared.generated.resources.menu_recipes
import foodlist.app.shared.generated.resources.menu_storage
import org.jetbrains.compose.resources.StringResource

sealed class BottomMenu(
    val icon: ImageVector,
    val label: StringResource
) {
    data object Dashboard: BottomMenu(
        icon = Icons.Filled.Dashboard,
        label = Res.string.menu_dashboard
    )
    data object Storage: BottomMenu(
        icon = Icons.Default.Inventory2,
        label = Res.string.menu_storage
    )
    data object Recipes: BottomMenu(
        icon = Icons.AutoMirrored.Default.MenuBook,
        label = Res.string.menu_recipes
    )
    data object Profile: BottomMenu(
        icon = Icons.Filled.Person,
        label = Res.string.menu_profile
    )
}