package io.github.alefaux.foodlist.feature.productdetail.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.common_cancel
import foodlist.app.shared.generated.resources.discard_product_dialog_confirm
import foodlist.app.shared.generated.resources.discard_product_dialog_message
import foodlist.app.shared.generated.resources.discard_product_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscardProductDialog(
    productName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(Res.string.discard_product_dialog_title, productName))
        },
        text = {
            Text(stringResource(Res.string.discard_product_dialog_message))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(Res.string.discard_product_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.common_cancel))
            }
        }
    )
}
