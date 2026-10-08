package io.github.alefaux.foodlist.feature.recipes.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_edit_remove
import foodlist.app.shared.generated.resources.recipe_edit_step_instructions
import foodlist.app.shared.generated.resources.recipe_edit_step_timer
import foodlist.app.shared.generated.resources.recipe_edit_step_title
import foodlist.app.shared.generated.resources.recipe_edit_unit_minutes
import io.github.alefaux.foodlist.feature.recipes.modelui.EditableStep
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditableStepCard(
    position: Int,
    step: EditableStep,
    onTitleChange: (String) -> Unit,
    onInstructionsChange: (String) -> Unit,
    onTimerChange: (String) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary
                    ) {}
                    Text(
                        text = position.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    modifier = Modifier.width(132.dp),
                    value = step.timerMinutes,
                    onValueChange = onTimerChange,
                    placeholder = { Text(stringResource(Res.string.recipe_edit_step_timer)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    suffix = { Text(stringResource(Res.string.recipe_edit_unit_minutes)) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(Res.string.recipe_edit_remove),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = step.title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(Res.string.recipe_edit_step_title)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = step.instructions,
                onValueChange = onInstructionsChange,
                label = { Text(stringResource(Res.string.recipe_edit_step_instructions)) },
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
@Preview
private fun EditableStepCardPreview() {
    MaterialTheme {
        EditableStepCard(
            position = 1,
            step = EditableStep(
                key = 0,
                title = "Cook the spaghetti",
                instructions = "Bring a large pot of salted water to a boil and cook the spaghetti until al dente.",
                timerMinutes = "10"
            ),
            onTitleChange = {},
            onInstructionsChange = {},
            onTimerChange = {},
            onRemove = {}
        )
    }
}
