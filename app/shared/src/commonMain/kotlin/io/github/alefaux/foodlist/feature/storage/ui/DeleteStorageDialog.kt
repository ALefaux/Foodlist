package io.github.alefaux.foodlist.feature.storage.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.common_cancel
import foodlist.app.shared.generated.resources.common_delete
import foodlist.app.shared.generated.resources.delete_storage_dialog_message
import foodlist.app.shared.generated.resources.delete_storage_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteStorageDialog(
    storageName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(Res.string.delete_storage_dialog_title, storageName))
        },
        text = {
            Text(stringResource(Res.string.delete_storage_dialog_message, storageName))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(Res.string.common_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.common_cancel))
            }
        }
    )
}
