package io.github.alefaux.foodlist.feature.recipes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipeDetailUseCase
import io.github.alefaux.foodlist.feature.recipes.presentation.model.RecipeDetailUiState
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeId: Long,
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getRecipeDetailUseCase(recipeId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load recipe detail for $recipeId")
            }.onSuccess { recipe ->
                _uiState.update { it.copy(recipe = recipe) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun toggleStep(position: Int) {
        _uiState.update { state ->
            val completed = if (position in state.completedSteps) {
                state.completedSteps - position
            } else {
                state.completedSteps + position
            }
            state.copy(completedSteps = completed.toImmutableSet())
        }
    }
}
