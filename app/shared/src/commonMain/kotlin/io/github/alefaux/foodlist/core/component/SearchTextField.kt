package io.github.alefaux.foodlist.core.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.search_pantry_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchChange: (query: String) -> Unit = {},
    query: String = ""
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                text = stringResource(Res.string.search_pantry_placeholder)
            )
        },
        leadingIcon = {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.Search
            )
        },
        onValueChange = onSearchChange,
        shape = RoundedCornerShape(16.dp),
        value = query
    )
}