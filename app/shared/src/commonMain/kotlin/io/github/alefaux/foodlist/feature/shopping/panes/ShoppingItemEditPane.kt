package io.github.alefaux.foodlist.feature.shopping.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_edit_brands_label
import foodlist.app.shared.generated.resources.shopping_edit_department
import foodlist.app.shared.generated.resources.shopping_edit_item_title
import foodlist.app.shared.generated.resources.shopping_edit_name_error
import foodlist.app.shared.generated.resources.shopping_edit_name_label
import foodlist.app.shared.generated.resources.shopping_edit_note_label
import foodlist.app.shared.generated.resources.shopping_edit_notes_title
import foodlist.app.shared.generated.resources.shopping_edit_open_recipe
import foodlist.app.shared.generated.resources.shopping_edit_package_size
import foodlist.app.shared.generated.resources.shopping_edit_package_size_hint
import foodlist.app.shared.generated.resources.shopping_edit_packages
import foodlist.app.shared.generated.resources.shopping_edit_priority_subtitle
import foodlist.app.shared.generated.resources.shopping_edit_priority_title
import foodlist.app.shared.generated.resources.shopping_edit_quantity_title
import foodlist.app.shared.generated.resources.shopping_edit_remove
import foodlist.app.shared.generated.resources.shopping_edit_required_for
import foodlist.app.shared.generated.resources.shopping_edit_status_purchased
import foodlist.app.shared.generated.resources.shopping_edit_status_to_buy
import foodlist.app.shared.generated.resources.shopping_edit_storage_empty
import foodlist.app.shared.generated.resources.shopping_edit_storage_none
import foodlist.app.shared.generated.resources.shopping_edit_storage_purchased_hint
import foodlist.app.shared.generated.resources.shopping_edit_storage_subtitle
import foodlist.app.shared.generated.resources.shopping_edit_storage_title
import io.github.alefaux.foodlist.core.component.EditSection
import io.github.alefaux.foodlist.core.component.Stepper
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingItemEditUiState
import io.github.alefaux.foodlist.feature.shopping.ui.PriorityOption
import io.github.alefaux.foodlist.feature.shopping.ui.StorageOptionCard
import io.github.alefaux.foodlist.feature.storage.ui.storageUnitIcon
import org.jetbrains.compose.resources.stringResource

/** Callbacks of the edit shopping item form. */
class ShoppingItemEditActions(
    val onNameChange: (String) -> Unit,
    val onCategorySelected: (String) -> Unit,
    val onCountDecrease: () -> Unit,
    val onCountIncrease: () -> Unit,
    val onQuantityChange: (String) -> Unit,
    val onStorageSelected: (Long?) -> Unit,
    val onPrioritySelected: (ShoppingPriority) -> Unit,
    val onBrandsChange: (String) -> Unit,
    val onNoteChange: (String) -> Unit,
    val onRecipeClick: (Long) -> Unit,
    val onRemoveClick: () -> Unit
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShoppingItemEditPane(
    state: ShoppingItemEditUiState,
    actions: ShoppingItemEditActions,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StatusHeader(state = state, onRecipeClick = actions.onRecipeClick)
        }

        item {
            EditSection(title = stringResource(Res.string.shopping_edit_item_title)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    onValueChange = actions.onNameChange,
                    label = { Text(stringResource(Res.string.shopping_edit_name_label)) },
                    isError = state.isNameError,
                    supportingText = if (state.isNameError) {
                        { Text(stringResource(Res.string.shopping_edit_name_error)) }
                    } else {
                        null
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                Text(
                    text = stringResource(Res.string.shopping_edit_department),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.categories.forEach { category ->
                        FilterChip(
                            selected = category == state.category,
                            onClick = { actions.onCategorySelected(category) },
                            label = { Text(category) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }

        item {
            EditSection(title = stringResource(Res.string.shopping_edit_quantity_title)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Stepper(
                        modifier = Modifier.weight(1f),
                        value = state.count,
                        label = stringResource(Res.string.shopping_edit_packages),
                        onDecrease = actions.onCountDecrease,
                        onIncrease = actions.onCountIncrease
                    )
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.quantity,
                        onValueChange = actions.onQuantityChange,
                        label = { Text(stringResource(Res.string.shopping_edit_package_size)) },
                        placeholder = { Text(stringResource(Res.string.shopping_edit_package_size_hint)) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        item {
            EditSection(
                title = stringResource(Res.string.shopping_edit_storage_title),
                subtitle = stringResource(
                    if (state.isPurchased) {
                        Res.string.shopping_edit_storage_purchased_hint
                    } else {
                        Res.string.shopping_edit_storage_subtitle
                    }
                )
            ) {
                if (state.storages.isEmpty()) {
                    Text(
                        text = stringResource(Res.string.shopping_edit_storage_empty),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.storages, key = { it.id }) { storage ->
                            StorageOptionCard(
                                icon = storageUnitIcon(storage.name),
                                label = storage.name,
                                selected = storage.id == state.storageId,
                                onClick = { actions.onStorageSelected(storage.id) }
                            )
                        }
                        item {
                            StorageOptionCard(
                                icon = Icons.Filled.DoNotDisturbOn,
                                label = stringResource(Res.string.shopping_edit_storage_none),
                                selected = state.storageId == null,
                                onClick = { actions.onStorageSelected(null) }
                            )
                        }
                    }
                }
            }
        }

        item {
            EditSection(
                title = stringResource(Res.string.shopping_edit_priority_title),
                subtitle = stringResource(Res.string.shopping_edit_priority_subtitle)
            ) {
                ShoppingPriority.entries.forEach { priority ->
                    PriorityOption(
                        priority = priority,
                        selected = priority == state.priority,
                        onClick = { actions.onPrioritySelected(priority) }
                    )
                }
            }
        }

        item {
            EditSection(title = stringResource(Res.string.shopping_edit_notes_title)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.brands,
                    onValueChange = actions.onBrandsChange,
                    label = { Text(stringResource(Res.string.shopping_edit_brands_label)) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.note,
                    onValueChange = actions.onNoteChange,
                    label = { Text(stringResource(Res.string.shopping_edit_note_label)) },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        item {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = actions.onRemoveClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                Text(
                    text = stringResource(Res.string.shopping_edit_remove),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusHeader(
    state: ShoppingItemEditUiState,
    onRecipeClick: (Long) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(
                if (state.isPurchased) Res.string.shopping_edit_status_purchased else Res.string.shopping_edit_status_to_buy
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )

        val recipeId = state.recipeId
        if (recipeId != null && state.recipeName != null) {
            Card(
                onClick = { onRecipeClick(recipeId) },
                colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = colorScheme.primary
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(Res.string.shopping_edit_required_for),
                            style = MaterialTheme.typography.labelSmall,
                            color = colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = state.recipeName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = stringResource(Res.string.shopping_edit_open_recipe),
                        tint = colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
