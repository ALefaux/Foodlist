package io.github.alefaux.foodlist.feature.recipes.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_edit_ingredient_name
import foodlist.app.shared.generated.resources.recipe_edit_ingredient_quantity
import foodlist.app.shared.generated.resources.recipe_edit_remove
import io.github.alefaux.foodlist.feature.recipes.modelui.EditableIngredient
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditableIngredientRow(
    ingredient: EditableIngredient,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(0.6f),
            value = ingredient.name,
            onValueChange = onNameChange,
            label = { Text(stringResource(Res.string.recipe_edit_ingredient_name)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            modifier = Modifier.weight(0.4f),
            value = ingredient.quantity,
            onValueChange = onQuantityChange,
            label = { Text(stringResource(Res.string.recipe_edit_ingredient_quantity)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = stringResource(Res.string.recipe_edit_remove),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
@Preview
private fun EditableIngredientRowPreview() {
    MaterialTheme {
        EditableIngredientRow(
            ingredient = EditableIngredient(key = 0, name = "Spaghetti", quantity = "200 g"),
            onNameChange = {},
            onQuantityChange = {},
            onRemove = {}
        )
    }
}
