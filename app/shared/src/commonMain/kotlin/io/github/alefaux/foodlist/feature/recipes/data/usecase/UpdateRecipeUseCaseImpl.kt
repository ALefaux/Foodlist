package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraft
import io.github.alefaux.foodlist.feature.recipes.domain.UpdateRecipeUseCase

class UpdateRecipeUseCaseImpl(
    private val repository: RecipesRepository
) : UpdateRecipeUseCase {
    override suspend fun invoke(recipeId: Long, draft: RecipeDraft) = repository.updateRecipe(recipeId, draft)
}
