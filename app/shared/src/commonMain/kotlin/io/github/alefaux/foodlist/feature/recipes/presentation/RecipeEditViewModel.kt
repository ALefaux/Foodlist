package io.github.alefaux.foodlist.feature.recipes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.recipes.domain.DeleteRecipeUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipeDetailUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraft
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftIngredient
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftStep
import io.github.alefaux.foodlist.feature.recipes.domain.UpdateRecipeUseCase
import io.github.alefaux.foodlist.feature.recipes.modelui.EditableIngredient
import io.github.alefaux.foodlist.feature.recipes.modelui.EditableStep
import io.github.alefaux.foodlist.feature.recipes.presentation.model.RecipeEditUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeEditViewModel(
    private val recipeId: Long,
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeEditUiState())
    val uiState: StateFlow<RecipeEditUiState> = _uiState

    private var nextKey = 0L

    init {
        // Loaded once: reloading on resume would wipe what the user typed.
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getRecipeDetailUseCase(recipeId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load recipe $recipeId for editing")
                _uiState.update { it.copy(isLoading = false, isNotFound = true) }
            }.onSuccess { recipe ->
                if (recipe == null) {
                    _uiState.update { it.copy(isLoading = false, isNotFound = true) }
                    return@onSuccess
                }

                _uiState.update {
                    it.copy(
                        name = recipe.name,
                        description = recipe.description,
                        isVegetarian = recipe.isVegetarian,
                        prepMinutes = recipe.prepMinutes.toString(),
                        cookMinutes = recipe.cookMinutes.toString(),
                        servings = recipe.servings ?: 1,
                        calories = recipe.calories?.toString().orEmpty(),
                        tip = recipe.tip.orEmpty(),
                        ingredients = recipe.ingredients.map { ingredient ->
                            EditableIngredient(key = nextKey++, name = ingredient.name, quantity = ingredient.quantity)
                        }.toImmutableList(),
                        steps = recipe.steps.map { step ->
                            EditableStep(
                                key = nextKey++,
                                title = step.title,
                                instructions = step.instructions,
                                timerMinutes = step.timerMinutes?.toString().orEmpty()
                            )
                        }.toImmutableList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    // General info

    fun updateName(name: String) = _uiState.update { it.copy(name = name, isNameError = false) }

    fun updateDescription(description: String) = _uiState.update { it.copy(description = description) }

    fun toggleVegetarian() = _uiState.update { it.copy(isVegetarian = !it.isVegetarian) }

    // Time & yield

    fun updatePrepMinutes(value: String) = _uiState.update { it.copy(prepMinutes = value.digitsOnly()) }

    fun updateCookMinutes(value: String) = _uiState.update { it.copy(cookMinutes = value.digitsOnly()) }

    fun updateCalories(value: String) = _uiState.update { it.copy(calories = value.digitsOnly()) }

    fun increaseServings() = _uiState.update { it.copy(servings = (it.servings + 1).coerceAtMost(MAX_SERVINGS)) }

    fun decreaseServings() = _uiState.update { it.copy(servings = (it.servings - 1).coerceAtLeast(1)) }

    // Ingredients

    fun addIngredient() = _uiState.update {
        it.copy(ingredients = (it.ingredients + EditableIngredient(nextKey++, "", "")).toImmutableList())
    }

    fun updateIngredientName(key: Long, name: String) = updateIngredient(key) { it.copy(name = name) }

    fun updateIngredientQuantity(key: Long, quantity: String) = updateIngredient(key) { it.copy(quantity = quantity) }

    fun removeIngredient(key: Long) = _uiState.update { state ->
        state.copy(ingredients = state.ingredients.filterNot { it.key == key }.toImmutableList())
    }

    private fun updateIngredient(key: Long, transform: (EditableIngredient) -> EditableIngredient) =
        _uiState.update { state ->
            state.copy(ingredients = state.ingredients.map { if (it.key == key) transform(it) else it }.toImmutableList())
        }

    // Steps

    fun addStep() = _uiState.update {
        it.copy(steps = (it.steps + EditableStep(nextKey++, "", "", "")).toImmutableList())
    }

    fun updateStepTitle(key: Long, title: String) = updateStep(key) { it.copy(title = title) }

    fun updateStepInstructions(key: Long, instructions: String) = updateStep(key) { it.copy(instructions = instructions) }

    fun updateStepTimer(key: Long, minutes: String) = updateStep(key) { it.copy(timerMinutes = minutes.digitsOnly()) }

    fun removeStep(key: Long) = _uiState.update { state ->
        state.copy(steps = state.steps.filterNot { it.key == key }.toImmutableList())
    }

    private fun updateStep(key: Long, transform: (EditableStep) -> EditableStep) =
        _uiState.update { state ->
            state.copy(steps = state.steps.map { if (it.key == key) transform(it) else it }.toImmutableList())
        }

    // Tip

    fun updateTip(tip: String) = _uiState.update { it.copy(tip = tip) }

    // Save & delete

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
                updateRecipeUseCase(recipeId, state.toDraft())
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't save recipe $recipeId")
                _uiState.update { it.copy(isSaving = false) }
            }.onSuccess {
                _uiState.update { it.copy(isSaving = false, isSaved = true) }
            }
        }
    }

    fun showDeleteDialog() = _uiState.update { it.copy(isDeleteDialogVisible = true) }

    fun dismissDeleteDialog() = _uiState.update { it.copy(isDeleteDialogVisible = false) }

    fun deleteRecipe() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                deleteRecipeUseCase(recipeId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't delete recipe $recipeId")
            }.onSuccess {
                _uiState.update { it.copy(isDeleteDialogVisible = false, isDeleted = true) }
            }
        }
    }

    /** Empty ingredients and steps are dropped; blank optional fields are saved as missing. */
    private fun RecipeEditUiState.toDraft() = RecipeDraft(
        name = name.trim(),
        description = description.trim(),
        prepMinutes = prepMinutes.toIntOrNull() ?: 0,
        cookMinutes = cookMinutes.toIntOrNull() ?: 0,
        servings = servings,
        calories = calories.toIntOrNull(),
        isVegetarian = isVegetarian,
        tip = tip.trim().ifEmpty { null },
        ingredients = ingredients
            .filter { it.name.isNotBlank() }
            .map { RecipeDraftIngredient(name = it.name.trim(), quantity = it.quantity.trim()) },
        steps = steps
            .filter { it.title.isNotBlank() || it.instructions.isNotBlank() }
            .map {
                RecipeDraftStep(
                    title = it.title.trim(),
                    instructions = it.instructions.trim(),
                    timerMinutes = it.timerMinutes.toIntOrNull()?.takeIf { minutes -> minutes > 0 }
                )
            }
    )

    private fun String.digitsOnly(): String = filter { it.isDigit() }.take(MAX_DIGITS)

    private companion object {
        const val MAX_DIGITS = 4
        const val MAX_SERVINGS = 99
    }
}
