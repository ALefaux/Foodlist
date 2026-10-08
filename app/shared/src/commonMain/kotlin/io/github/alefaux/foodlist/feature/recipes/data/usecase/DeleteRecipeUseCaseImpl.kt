package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.DeleteRecipeUseCase

class DeleteRecipeUseCaseImpl(
    private val repository: RecipesRepository
) : DeleteRecipeUseCase {
    override suspend fun invoke(recipeId: Long) = repository.deleteRecipe(recipeId)
}
