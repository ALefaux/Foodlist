package io.github.alefaux.foodlist.feature.dashboard.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardMenu(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton.Primary(
            icon = Icons.Outlined.PhotoCamera,
            onClick = {},
            title = "Scan"
        )
        ActionButton.Secondary(
            icon = Icons.Outlined.Add,
            onClick = onAddClick,
            title = "Add"
        )
        /* For V2
        ActionButton.Tertiary(
            icon = Icons.AutoMirrored.Outlined.MenuBook,
            onClick = {},
            title = "Receipt"
        )
        ActionButton.Tertiary(
            icon = Icons.Outlined.ShoppingCart,
            onClick = {},
            title = "List"
        )
         */
    }
}