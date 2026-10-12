package io.github.alefaux.foodlist.feature.shopping.presentation.model

import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftIngredient
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ShoppingListTab {
    TO_BUY,
    PURCHASED
}

data class ShoppingListUiState(
    val newItemName: String = "",
    val selectedTab: ShoppingListTab = ShoppingListTab.TO_BUY,
    val toBuy: ImmutableList<ShoppingItem> = persistentListOf(),
    /** Most recently purchased first. */
    val purchased: ImmutableList<ShoppingItem> = persistentListOf(),
    /** Missing recipe ingredients that are not on the list yet. */
    val suggestions: ImmutableList<RecipeDraftIngredient> = persistentListOf(),
    val isLoading: Boolean = true
)
