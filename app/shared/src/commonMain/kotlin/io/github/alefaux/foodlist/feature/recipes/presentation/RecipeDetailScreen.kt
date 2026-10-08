package io.github.alefaux.foodlist.feature.recipes.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_not_found
import foodlist.app.shared.generated.resources.recipe_detail_top_bar_title
import foodlist.app.shared.generated.resources.recipe_edit_top_bar_title
import io.github.alefaux.foodlist.feature.recipes.panes.RecipeDetailPane
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeDetailSkeleton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Long,
    onBackPress: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel = koinViewModel(parameters = { parametersOf(recipeId) })
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

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
                        text = stringResource(Res.string.recipe_detail_top_bar_title),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    if (state.recipe != null) {
                        IconButton(onClick = onEditClick) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(Res.string.recipe_edit_top_bar_title),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        val recipe = state.recipe

        when {
            state.isLoading -> RecipeDetailSkeleton(modifier = Modifier.padding(padding))

            recipe == null -> Box(
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

            else -> RecipeDetailPane(
                modifier = Modifier.padding(padding),
                recipe = recipe,
                completedSteps = state.completedSteps,
                onStepToggle = viewModel::toggleStep
            )
        }
    }
}
