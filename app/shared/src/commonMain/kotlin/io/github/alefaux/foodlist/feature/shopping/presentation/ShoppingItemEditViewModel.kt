package io.github.alefaux.foodlist.feature.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.shopping.domain.DeleteShoppingItemUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemDetailUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.SHOPPING_CATEGORIES
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDraft
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import io.github.alefaux.foodlist.feature.shopping.domain.UpdateShoppingItemUseCase
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingItemEditUiState
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingItemEditViewModel(
    private val itemId: Long,
    private val getShoppingItemDetailUseCase: GetShoppingItemDetailUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val getStorageUnitsUseCase: GetStorageUnitsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingItemEditUiState())
    val uiState: StateFlow<ShoppingItemEditUiState> = _uiState

    init {
        // Loaded once: reloading on resume would wipe what the user typed.
        loadItem()
    }

    private fun loadItem() {
        viewModelScope.launch(dispatcher) {
            val storages = runCatching {
                getStorageUnitsUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load storage units")
            }.getOrDefault(emptyList())

            runCatching {
                getShoppingItemDetailUseCase(itemId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load shopping item $itemId for editing")
                _uiState.update { it.copy(isLoading = false, isNotFound = true) }
            }.onSuccess { detail ->
                if (detail == null) {
                    _uiState.update { it.copy(isLoading = false, isNotFound = true) }
                    return@onSuccess
                }

                val draft = detail.draft
                _uiState.update {
                    it.copy(
                        name = draft.name,
                        count = draft.count,
                        quantity = draft.quantity,
                        category = draft.category,
                        // Keep a category set elsewhere selectable
                        categories = (listOf(draft.category) + SHOPPING_CATEGORIES).distinct().toImmutableList(),
                        storageId = draft.storageId?.takeIf { id -> storages.any { storage -> storage.id == id } },
                        storages = storages.toImmutableList(),
                        priority = draft.priority,
                        brands = draft.brands,
                        note = draft.note,
                        isPurchased = detail.isPurchased,
                        recipeId = detail.recipeId,
                        recipeName = detail.recipeName,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateName(name: String) = _uiState.update { it.copy(name = name, isNameError = false) }

    fun selectCategory(category: String) = _uiState.update { it.copy(category = category) }

    fun increaseCount() = _uiState.update { it.copy(count = (it.count + 1).coerceAtMost(MAX_COUNT)) }

    fun decreaseCount() = _uiState.update { it.copy(count = (it.count - 1).coerceAtLeast(1)) }

    fun updateQuantity(quantity: String) = _uiState.update { it.copy(quantity = quantity) }

    fun selectStorage(storageId: Long?) = _uiState.update { it.copy(storageId = storageId) }

    fun selectPriority(priority: ShoppingPriority) = _uiState.update { it.copy(priority = priority) }

    fun updateBrands(brands: String) = _uiState.update { it.copy(brands = brands) }

    fun updateNote(note: String) = _uiState.update { it.copy(note = note) }

    fun save() {
        val state = _uiState.value
        if (state.isSaving) return

        if (state.name.isBlank()) {
            _uiState.update { it.copy(isNameError = true) }
            return
        }

        _uiState.update { it.copy(isSaving = true) }

        viewModelScope.launch(dispatcher) {
            runCatching {
                updateShoppingItemUseCase(
                    itemId,
                    ShoppingItemDraft(
                        name = state.name.trim(),
                        quantity = state.quantity.trim(),
                        count = state.count,
                        category = state.category,
                        storageId = state.storageId,
                        priority = state.priority,
                        brands = state.brands.trim(),
                        note = state.note.trim()
                    )
                )
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't save shopping item $itemId")
                _uiState.update { it.copy(isSaving = false) }
            }.onSuccess {
                _uiState.update { it.copy(isSaving = false, isSaved = true) }
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                deleteShoppingItemUseCase(itemId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't delete shopping item $itemId")
            }.onSuccess {
                _uiState.update { it.copy(isDeleted = true) }
            }
        }
    }

    private companion object {
        const val MAX_COUNT = 99
    }
}
