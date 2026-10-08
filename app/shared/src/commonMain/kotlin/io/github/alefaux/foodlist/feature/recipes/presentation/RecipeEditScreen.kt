package io.github.alefaux.foodlist.feature.recipes.presentation

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
import foodlist.app.shared.generated.resources.recipe_detail_not_found
import foodlist.app.shared.generated.resources.recipe_edit_save
import foodlist.app.shared.generated.resources.recipe_edit_top_bar_title
import io.github.alefaux.foodlist.feature.recipes.panes.RecipeEditActions
import io.github.alefaux.foodlist.feature.recipes.panes.RecipeEditPane
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeDetailSkeleton
import io.github.alefaux.foodlist.feature.recipes.ui.edit.DeleteRecipeDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEditScreen(
    recipeId: Long,
    onBackPress: () -> Unit,
    onRecipeSaved: () -> Unit,
    onRecipeDeleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeEditViewModel = koinViewModel(parameters = { parametersOf(recipeId) })
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onRecipeSaved()
    }
    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onRecipeDeleted()
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
                        text = stringResource(Res.string.recipe_edit_top_bar_title),
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
                                text = stringResource(Res.string.recipe_edit_save),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> RecipeDetailSkeleton(modifier = Modifier.padding(padding))

            state.isNotFound -> Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.recipe_detail_not_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> RecipeEditPane(
                modifier = Modifier.padding(padding),
                state = state,
                actions = RecipeEditActions(
                    onNameChange = viewModel::updateName,
                    onDescriptionChange = viewModel::updateDescription,
                    onVegetarianToggle = viewModel::toggleVegetarian,
                    onPrepMinutesChange = viewModel::updatePrepMinutes,
                    onCookMinutesChange = viewModel::updateCookMinutes,
                    onCaloriesChange = viewModel::updateCalories,
                    onServingsDecrease = viewModel::decreaseServings,
                    onServingsIncrease = viewModel::increaseServings,
                    onAddIngredient = viewModel::addIngredient,
                    onIngredientNameChange = viewModel::updateIngredientName,
                    onIngredientQuantityChange = viewModel::updateIngredientQuantity,
                    onRemoveIngredient = viewModel::removeIngredient,
                    onAddStep = viewModel::addStep,
                    onStepTitleChange = viewModel::updateStepTitle,
                    onStepInstructionsChange = viewModel::updateStepInstructions,
                    onStepTimerChange = viewModel::updateStepTimer,
                    onRemoveStep = viewModel::removeStep,
                    onTipChange = viewModel::updateTip,
                    onDeleteClick = viewModel::showDeleteDialog
                )
            )
        }
    }

    if (state.isDeleteDialogVisible) {
        DeleteRecipeDialog(
            recipeName = state.name,
            onDismiss = viewModel::dismissDeleteDialog,
            onConfirm = viewModel::deleteRecipe
        )
    }
}
