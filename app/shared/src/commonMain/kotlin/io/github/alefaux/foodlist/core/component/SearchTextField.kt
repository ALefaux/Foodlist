package io.github.alefaux.foodlist.core.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                text = "Search your pantry..."
            )
        },
        leadingIcon = {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.Search
            )
        },
        onValueChange = {},
        shape = RoundedCornerShape(16.dp),
        value = ""
    )
}