package io.github.alefaux.foodlist.feature.recipes.domain

interface UpdateRecipeUseCase {
    suspend operator fun invoke(recipeId: Long, draft: RecipeDraft)
}
