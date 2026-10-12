package io.github.alefaux.foodlist.feature.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.recipes.domain.GetMissingRecipeIngredientsUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftIngredient
import io.github.alefaux.foodlist.feature.shopping.domain.AddShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.DeleteShoppingItemUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.SetShoppingItemPurchasedUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingListTab
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingListUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val addShoppingItemsUseCase: AddShoppingItemsUseCase,
    private val setShoppingItemPurchasedUseCase: SetShoppingItemPurchasedUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val getMissingRecipeIngredientsUseCase: GetMissingRecipeIngredientsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState

    private var items: List<ShoppingItem> = emptyList()
    private var missingIngredients: List<RecipeDraftIngredient> = emptyList()

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getMissingRecipeIngredientsUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load missing recipe ingredients")
            }.onSuccess { ingredients ->
                missingIngredients = ingredients
            }
            refreshItems()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun updateNewItemName(name: String) = _uiState.update { it.copy(newItemName = name) }

    fun selectTab(tab: ShoppingListTab) = _uiState.update { it.copy(selectedTab = tab) }

    fun addItem() {
        val name = _uiState.value.newItemName.trim()
        if (name.isEmpty()) return

        _uiState.update { it.copy(newItemName = "") }
        addItems(listOf(NewShoppingItem(name = name, quantity = "")))
    }

    fun addAllSuggestions() {
        addItems(_uiState.value.suggestions.map { NewShoppingItem(name = it.name, quantity = it.quantity) })
    }

    private fun addItems(newItems: List<NewShoppingItem>) {
        if (newItems.isEmpty()) return

        viewModelScope.launch(dispatcher) {
            runCatching {
                addShoppingItemsUseCase(newItems)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't add shopping items")
            }
            refreshItems()
        }
    }

    fun setPurchased(itemId: Long, isPurchased: Boolean) {
        viewModelScope.launch(dispatcher) {
            runCatching {
                setShoppingItemPurchasedUseCase(itemId, isPurchased)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't update shopping item $itemId")
            }
            refreshItems()
        }
    }

    fun deleteItem(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            runCatching {
                deleteShoppingItemUseCase(itemId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't delete shopping item $itemId")
            }
            refreshItems()
        }
    }

    private suspend fun refreshItems() {
        runCatching {
            getShoppingItemsUseCase()
        }.onFailure { error ->
            AppLogging.e(error, "Couldn't load shopping items")
        }.onSuccess { loaded ->
            items = loaded
        }

        val toBuy = items.filterNot { it.isPurchased }
        val listedNames = toBuy.map { it.name.trim().lowercase() }.toSet()

        _uiState.update { state ->
            state.copy(
                toBuy = toBuy.toImmutableList(),
                purchased = items
                    .filter { it.isPurchased }
                    .sortedByDescending { it.purchasedAt }
                    .toImmutableList(),
                suggestions = missingIngredients
                    .filterNot { it.name.trim().lowercase() in listedNames }
                    .toImmutableList()
            )
        }
    }
}
