package io.github.alefaux.foodlist.feature.recipes.presentation.model

import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeFilter
import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RecipesUiState(
    val query: String = "",
    val selectedFilter: RecipeFilter = RecipeFilter.ALL,
    val recipes: ImmutableList<RecipeUi> = persistentListOf(),
    val isLoading: Boolean = true
)
