package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.storage_header_subtitle
import foodlist.app.shared.generated.resources.storage_header_title
import io.github.alefaux.foodlist.core.component.HomeHeader
import org.jetbrains.compose.resources.stringResource

@Composable
fun StorageHeader(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeHeader(
        modifier = modifier,
        endSlot = {
            FilledIconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        subtitle = stringResource(Res.string.storage_header_subtitle),
        title = stringResource(Res.string.storage_header_title),
    )
}
