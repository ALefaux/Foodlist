package io.github.alefaux.foodlist.feature.shopping.presentation.model

import io.github.alefaux.foodlist.feature.shopping.domain.SHOPPING_CATEGORIES
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ShoppingItemEditUiState(
    val name: String = "",
    val isNameError: Boolean = false,
    val count: Int = 1,
    val quantity: String = "",
    val category: String = "Other",
    val categories: ImmutableList<String> = SHOPPING_CATEGORIES.toImmutableList(),
    val storageId: Long? = null,
    val storages: ImmutableList<StorageUnit> = persistentListOf(),
    val priority: ShoppingPriority = ShoppingPriority.STANDARD,
    val brands: String = "",
    val note: String = "",
    val isPurchased: Boolean = false,
    val recipeId: Long? = null,
    val recipeName: String? = null,
    val isLoading: Boolean = true,
    val isNotFound: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)
