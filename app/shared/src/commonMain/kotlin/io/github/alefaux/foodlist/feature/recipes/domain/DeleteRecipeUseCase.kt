package io.github.alefaux.foodlist.feature.recipes.domain

interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeId: Long)
}
