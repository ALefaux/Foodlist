package io.github.alefaux.foodlist.feature.recipes.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_badge_vegetarian
import foodlist.app.shared.generated.resources.recipe_detail_energy
import foodlist.app.shared.generated.resources.recipe_detail_servings
import foodlist.app.shared.generated.resources.recipe_detail_steps_title
import foodlist.app.shared.generated.resources.recipe_detail_tip_title
import foodlist.app.shared.generated.resources.recipe_edit_add_ingredient
import foodlist.app.shared.generated.resources.recipe_edit_add_step
import foodlist.app.shared.generated.resources.recipe_edit_cook_label
import foodlist.app.shared.generated.resources.recipe_edit_delete_button
import foodlist.app.shared.generated.resources.recipe_edit_delete_hint
import foodlist.app.shared.generated.resources.recipe_edit_description_label
import foodlist.app.shared.generated.resources.recipe_edit_general_title
import foodlist.app.shared.generated.resources.recipe_edit_ingredients_count
import foodlist.app.shared.generated.resources.recipe_edit_ingredients_title
import foodlist.app.shared.generated.resources.recipe_edit_name_error
import foodlist.app.shared.generated.resources.recipe_edit_name_label
import foodlist.app.shared.generated.resources.recipe_edit_prep_label
import foodlist.app.shared.generated.resources.recipe_edit_steps_count
import foodlist.app.shared.generated.resources.recipe_edit_tip_hint
import foodlist.app.shared.generated.resources.recipe_edit_time_title
import foodlist.app.shared.generated.resources.recipe_edit_unit_kcal
import foodlist.app.shared.generated.resources.recipe_edit_unit_minutes
import io.github.alefaux.foodlist.feature.recipes.presentation.model.RecipeEditUiState
import io.github.alefaux.foodlist.feature.recipes.ui.edit.EditSection
import io.github.alefaux.foodlist.feature.recipes.ui.edit.EditableIngredientRow
import io.github.alefaux.foodlist.feature.recipes.ui.edit.EditableStepCard
import io.github.alefaux.foodlist.feature.recipes.ui.edit.NumberField
import io.github.alefaux.foodlist.feature.recipes.ui.edit.Stepper
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/** Callbacks of the edit recipe form, grouped to keep [RecipeEditPane]'s signature readable. */
class RecipeEditActions(
    val onNameChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
    val onVegetarianToggle: () -> Unit,
    val onPrepMinutesChange: (String) -> Unit,
    val onCookMinutesChange: (String) -> Unit,
    val onCaloriesChange: (String) -> Unit,
    val onServingsDecrease: () -> Unit,
    val onServingsIncrease: () -> Unit,
    val onAddIngredient: () -> Unit,
    val onIngredientNameChange: (Long, String) -> Unit,
    val onIngredientQuantityChange: (Long, String) -> Unit,
    val onRemoveIngredient: (Long) -> Unit,
    val onAddStep: () -> Unit,
    val onStepTitleChange: (Long, String) -> Unit,
    val onStepInstructionsChange: (Long, String) -> Unit,
    val onStepTimerChange: (Long, String) -> Unit,
    val onRemoveStep: (Long) -> Unit,
    val onTipChange: (String) -> Unit,
    val onDeleteClick: () -> Unit
)

@Composable
fun RecipeEditPane(
    state: RecipeEditUiState,
    actions: RecipeEditActions,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            EditSection(title = stringResource(Res.string.recipe_edit_general_title)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    onValueChange = actions.onNameChange,
                    label = { Text(stringResource(Res.string.recipe_edit_name_label)) },
                    isError = state.isNameError,
                    supportingText = if (state.isNameError) {
                        { Text(stringResource(Res.string.recipe_edit_name_error)) }
                    } else {
                        null
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.description,
                    onValueChange = actions.onDescriptionChange,
                    label = { Text(stringResource(Res.string.recipe_edit_description_label)) },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )
                FilterChip(
                    selected = state.isVegetarian,
                    onClick = actions.onVegetarianToggle,
                    label = { Text(stringResource(Res.string.recipe_detail_badge_vegetarian)) },
                    leadingIcon = if (state.isVegetarian) {
                        { Icon(imageVector = Icons.Filled.Check, contentDescription = null) }
                    } else {
                        null
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    )
                )
            }
        }

        item {
            val minutes = stringResource(Res.string.recipe_edit_unit_minutes)

            EditSection(title = stringResource(Res.string.recipe_edit_time_title)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    NumberField(
                        modifier = Modifier.weight(1f),
                        value = state.prepMinutes,
                        onValueChange = actions.onPrepMinutesChange,
                        label = stringResource(Res.string.recipe_edit_prep_label),
                        unit = minutes
                    )
                    NumberField(
                        modifier = Modifier.weight(1f),
                        value = state.cookMinutes,
                        onValueChange = actions.onCookMinutesChange,
                        label = stringResource(Res.string.recipe_edit_cook_label),
                        unit = minutes
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Stepper(
                        modifier = Modifier.weight(1f),
                        value = state.servings,
                        label = stringResource(Res.string.recipe_detail_servings),
                        onDecrease = actions.onServingsDecrease,
                        onIncrease = actions.onServingsIncrease
                    )
                    NumberField(
                        modifier = Modifier.weight(1f),
                        value = state.calories,
                        onValueChange = actions.onCaloriesChange,
                        label = stringResource(Res.string.recipe_detail_energy),
                        unit = stringResource(Res.string.recipe_edit_unit_kcal)
                    )
                }
            }
        }

        item {
            EditSection(
                title = stringResource(Res.string.recipe_edit_ingredients_title),
                subtitle = pluralStringResource(
                    Res.plurals.recipe_edit_ingredients_count,
                    state.ingredients.size,
                    state.ingredients.size
                )
            ) {
                state.ingredients.forEach { ingredient ->
                    EditableIngredientRow(
                        ingredient = ingredient,
                        onNameChange = { actions.onIngredientNameChange(ingredient.key, it) },
                        onQuantityChange = { actions.onIngredientQuantityChange(ingredient.key, it) },
                        onRemove = { actions.onRemoveIngredient(ingredient.key) }
                    )
                }
                AddButton(
                    text = stringResource(Res.string.recipe_edit_add_ingredient),
                    onClick = actions.onAddIngredient
                )
            }
        }

        item {
            EditSection(
                title = stringResource(Res.string.recipe_detail_steps_title),
                subtitle = pluralStringResource(
                    Res.plurals.recipe_edit_steps_count,
                    state.steps.size,
                    state.steps.size
                )
            ) {
                state.steps.forEachIndexed { index, step ->
                    EditableStepCard(
                        position = index + 1,
                        step = step,
                        onTitleChange = { actions.onStepTitleChange(step.key, it) },
                        onInstructionsChange = { actions.onStepInstructionsChange(step.key, it) },
                        onTimerChange = { actions.onStepTimerChange(step.key, it) },
                        onRemove = { actions.onRemoveStep(step.key) }
                    )
                }
                AddButton(
                    text = stringResource(Res.string.recipe_edit_add_step),
                    onClick = actions.onAddStep
                )
            }
        }

        item {
            EditSection(
                title = stringResource(Res.string.recipe_detail_tip_title),
                subtitle = stringResource(Res.string.recipe_edit_tip_hint)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.tip,
                    onValueChange = actions.onTipChange,
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        item {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = actions.onDeleteClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.DeleteForever, contentDescription = null)
                    Text(
                        text = stringResource(Res.string.recipe_edit_delete_button),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.recipe_edit_delete_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun AddButton(
    text: String,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
