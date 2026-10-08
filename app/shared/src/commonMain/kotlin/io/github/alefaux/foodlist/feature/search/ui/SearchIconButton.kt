package io.github.alefaux.foodlist.feature.search.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
    }
}