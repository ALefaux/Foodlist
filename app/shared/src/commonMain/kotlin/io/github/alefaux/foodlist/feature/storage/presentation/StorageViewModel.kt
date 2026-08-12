package io.github.alefaux.foodlist.feature.storage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.storage.domain.AddStorageUnitUseCase
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import io.github.alefaux.foodlist.feature.storage.modelui.StorageUnitUi
import io.github.alefaux.foodlist.feature.storage.presentation.model.StorageUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StorageViewModel(
    private val getStorageUnitsUseCase: GetStorageUnitsUseCase,
    private val addStorageUnitUseCase: AddStorageUnitUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(StorageUiState())
    val uiState: StateFlow<StorageUiState> = _uiState

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getStorageUnitsUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load storage units")
            }.onSuccess { units ->
                _uiState.update { state ->
                    state.copy(
                        storageUnits = units.map {
                            StorageUnitUi(
                                id = it.id,
                                name = it.name,
                                productCount = it.productCount,
                                expiringCount = it.expiringCount
                            )
                        }.toImmutableList()
                    )
                }
            }
        }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(isAddDialogVisible = true) }
    }

    fun dismissAddDialog() {
        _uiState.update { it.copy(isAddDialogVisible = false) }
    }

    fun addStorageUnit(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isEmpty()) return

        viewModelScope.launch(dispatcher) {
            runCatching {
                addStorageUnitUseCase(trimmedName)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't add storage unit $trimmedName")
            }.onSuccess {
                _uiState.update { it.copy(isAddDialogVisible = false) }
                loadData()
            }
        }
    }
}
