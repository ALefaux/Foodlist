package io.github.alefaux.foodlist.feature.recipes.data.usecase

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipesUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.Recipe

class GetRecipesUseCaseImpl(
    private val repository: RecipesRepository
) : GetRecipesUseCase {
    override suspend fun invoke(): List<Recipe> = repository.getRecipes()
}
