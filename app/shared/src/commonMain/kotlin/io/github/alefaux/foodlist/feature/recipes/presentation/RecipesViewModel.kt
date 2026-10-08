package io.github.alefaux.foodlist.feature.recipes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipesUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeFilter
import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeUi
import io.github.alefaux.foodlist.feature.recipes.presentation.model.RecipesUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState

    private var allRecipes: List<RecipeUi> = emptyList()

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getRecipesUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load recipes")
            }.onSuccess { recipes ->
                allRecipes = recipes.map { recipe ->
                    RecipeUi(
                        id = recipe.id,
                        name = recipe.name,
                        ingredientsCount = recipe.ingredients.size,
                        durationMinutes = recipe.durationMinutes,
                        availability = recipe.availability
                    )
                }
                applyFilters()
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        applyFilters()
    }

    fun selectFilter(filter: RecipeFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        val query = state.query.trim()

        val filtered = allRecipes
            .filter { query.isEmpty() || it.name.contains(query, ignoreCase = true) }
            .filter { recipe ->
                when (state.selectedFilter) {
                    RecipeFilter.ALL -> true
                    RecipeFilter.CAN_MAKE -> recipe.availability != RecipeAvailability.MISSING_INGREDIENTS
                    RecipeFilter.MISSING_INGREDIENTS -> recipe.availability == RecipeAvailability.MISSING_INGREDIENTS
                }
            }

        _uiState.update { it.copy(recipes = filtered.toImmutableList()) }
    }
}
