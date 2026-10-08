package io.github.alefaux.foodlist.feature.recipes.presentation.model

import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

data class RecipeDetailUiState(
    val recipe: RecipeDetail? = null,
    /** Positions of the steps the user ticked. Kept in memory only. */
    val completedSteps: ImmutableSet<Int> = persistentSetOf(),
    val isLoading: Boolean = true
)
