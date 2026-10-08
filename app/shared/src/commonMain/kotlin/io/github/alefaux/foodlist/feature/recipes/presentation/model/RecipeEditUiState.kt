package io.github.alefaux.foodlist.feature.recipes.presentation.model

import io.github.alefaux.foodlist.feature.recipes.modelui.EditableIngredient
import io.github.alefaux.foodlist.feature.recipes.modelui.EditableStep
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RecipeEditUiState(
    val name: String = "",
    val isNameError: Boolean = false,
    val description: String = "",
    val isVegetarian: Boolean = false,
    val prepMinutes: String = "",
    val cookMinutes: String = "",
    val servings: Int = 1,
    val calories: String = "",
    val tip: String = "",
    val ingredients: ImmutableList<EditableIngredient> = persistentListOf(),
    val steps: ImmutableList<EditableStep> = persistentListOf(),
    val isLoading: Boolean = true,
    val isNotFound: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)
