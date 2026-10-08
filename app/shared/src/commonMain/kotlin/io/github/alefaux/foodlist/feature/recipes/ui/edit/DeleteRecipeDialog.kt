package io.github.alefaux.foodlist.feature.recipes.ui.edit

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.common_cancel
import foodlist.app.shared.generated.resources.common_delete
import foodlist.app.shared.generated.resources.delete_recipe_dialog_message
import foodlist.app.shared.generated.resources.delete_recipe_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteRecipeDialog(
    recipeName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(Res.string.delete_recipe_dialog_title, recipeName))
        },
        text = {
            Text(stringResource(Res.string.delete_recipe_dialog_message))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
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
