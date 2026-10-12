package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.GetMissingRecipeIngredientsUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.MissingIngredient

class GetMissingRecipeIngredientsUseCaseImpl(
    private val repository: RecipesRepository
) : GetMissingRecipeIngredientsUseCase {
    override suspend fun invoke(): List<MissingIngredient> = repository.getMissingIngredients()
}
