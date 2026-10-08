package io.github.alefaux.foodlist.feature.recipes.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alefaux.foodlist.feature.recipes.panes.RecipesPane
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipesScreen(
    onRecipeClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = koinViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier
    ) { padding ->
        RecipesPane(
            modifier = Modifier.padding(padding),
            state = state,
            onQueryChange = viewModel::updateQuery,
            onFilterSelected = viewModel::selectFilter,
            onRecipeClick = onRecipeClick
        )
    }
}
