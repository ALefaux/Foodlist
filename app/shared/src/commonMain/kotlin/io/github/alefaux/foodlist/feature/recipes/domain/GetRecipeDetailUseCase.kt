package io.github.alefaux.foodlist.feature.recipes.domain

interface GetRecipeDetailUseCase {
    suspend operator fun invoke(recipeId: Long): RecipeDetail?
}
