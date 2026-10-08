package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipeDetailUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail

class GetRecipeDetailUseCaseImpl(
    private val repository: RecipesRepository
) : GetRecipeDetailUseCase {
    override suspend fun invoke(recipeId: Long): RecipeDetail? = repository.getRecipeDetail(recipeId)
}
