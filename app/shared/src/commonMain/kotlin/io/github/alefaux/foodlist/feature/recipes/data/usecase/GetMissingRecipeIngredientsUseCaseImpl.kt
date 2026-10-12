package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.GetMissingRecipeIngredientsUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftIngredient

class GetMissingRecipeIngredientsUseCaseImpl(
    private val repository: RecipesRepository
) : GetMissingRecipeIngredientsUseCase {
    override suspend fun invoke(): List<RecipeDraftIngredient> = repository.getMissingIngredients()
}
