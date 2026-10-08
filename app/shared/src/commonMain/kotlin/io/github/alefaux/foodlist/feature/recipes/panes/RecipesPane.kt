package io.github.alefaux.foodlist.feature.recipes.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipes_empty
import foodlist.app.shared.generated.resources.recipes_filter_all
import foodlist.app.shared.generated.resources.recipes_filter_can_make
import foodlist.app.shared.generated.resources.recipes_filter_missing_ingredients
import foodlist.app.shared.generated.resources.recipes_search_placeholder
import foodlist.app.shared.generated.resources.recipes_title
import io.github.alefaux.foodlist.core.component.HomeHeader
import io.github.alefaux.foodlist.core.component.SearchTextField
import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeFilter
import io.github.alefaux.foodlist.feature.recipes.presentation.model.RecipesUiState
import io.github.alefaux.foodlist.feature.recipes.ui.RecipeCard
import io.github.alefaux.foodlist.feature.recipes.ui.RecipesSkeleton
import io.github.alefaux.foodlist.feature.storage.ui.CategoryFilterChip
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipesPane(
    state: RecipesUiState,
    onQueryChange: (String) -> Unit,
    onFilterSelected: (RecipeFilter) -> Unit,
    onRecipeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                HomeHeader(
                    title = stringResource(Res.string.recipes_title)
                )
                SearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = stringResource(Res.string.recipes_search_placeholder),
                    query = state.query,
                    onSearchChange = onQueryChange
                )
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(RecipeFilter.entries) { filter ->
                    CategoryFilterChip(
                        label = stringResource(filter.label),
                        selected = filter == state.selectedFilter,
                        onClick = { onFilterSelected(filter) }
                    )
                }
            }
        }

        if (state.isLoading) {
            item {
                RecipesSkeleton()
            }
        } else if (state.recipes.isEmpty()) {
            item {
                Text(
                    text = stringResource(Res.string.recipes_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(state.recipes, key = { it.id }) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) }
                )
            }
        }
    }
}

private val RecipeFilter.label: StringResource
    get() = when (this) {
        RecipeFilter.ALL -> Res.string.recipes_filter_all
        RecipeFilter.CAN_MAKE -> Res.string.recipes_filter_can_make
        RecipeFilter.MISSING_INGREDIENTS -> Res.string.recipes_filter_missing_ingredients
    }

@Composable
@Preview
private fun RecipesPanePreview() {
    MaterialTheme {
        RecipesPane(
            state = RecipesUiState(),
            onQueryChange = {},
            onFilterSelected = {},
            onRecipeClick = {}
        )
    }
}
