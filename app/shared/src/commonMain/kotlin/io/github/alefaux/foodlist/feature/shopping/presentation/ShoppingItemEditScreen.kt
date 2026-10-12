package io.github.alefaux.foodlist.feature.shopping.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.common_cancel
import foodlist.app.shared.generated.resources.common_save
import foodlist.app.shared.generated.resources.shopping_edit_not_found
import foodlist.app.shared.generated.resources.shopping_edit_top_bar_title
import io.github.alefaux.foodlist.feature.shopping.panes.ShoppingItemEditActions
import io.github.alefaux.foodlist.feature.shopping.panes.ShoppingItemEditPane
import io.github.alefaux.foodlist.feature.shopping.ui.ShoppingListSkeleton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingItemEditScreen(
    itemId: Long,
    onBackPress: () -> Unit,
    onDone: () -> Unit,
    onRecipeClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShoppingItemEditViewModel = koinViewModel(parameters = { parametersOf(itemId) })
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(state.isSaved, state.isDeleted) {
        if (state.isSaved || state.isDeleted) onDone()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(Res.string.shopping_edit_top_bar_title),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        bottomBar = {
            if (!state.isLoading && !state.isNotFound) {
                Surface(tonalElevation = 3.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FilledTonalButton(
                            modifier = Modifier.weight(1f),
                            onClick = onBackPress,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(stringResource(Res.string.common_cancel))
                        }
                        Button(
                            modifier = Modifier.weight(2f),
                            onClick = viewModel::save,
                            enabled = !state.isSaving,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                            Text(
                                text = stringResource(Res.string.common_save),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> ShoppingListSkeleton(modifier = Modifier.padding(padding).padding(16.dp))

            state.isNotFound -> Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.shopping_edit_not_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> ShoppingItemEditPane(
                modifier = Modifier.padding(padding),
                state = state,
                actions = ShoppingItemEditActions(
                    onNameChange = viewModel::updateName,
                    onCategorySelected = viewModel::selectCategory,
                    onCountDecrease = viewModel::decreaseCount,
                    onCountIncrease = viewModel::increaseCount,
                    onQuantityChange = viewModel::updateQuantity,
                    onStorageSelected = viewModel::selectStorage,
                    onPrioritySelected = viewModel::selectPriority,
                    onBrandsChange = viewModel::updateBrands,
                    onNoteChange = viewModel::updateNote,
                    onRecipeClick = onRecipeClick,
                    onRemoveClick = viewModel::deleteItem
                )
            )
        }
    }
}
