package io.github.alefaux.foodlist.feature.recipes.domain

/** Ingredients of any recipe that the pantry can't provide, each name listed once. */
interface GetMissingRecipeIngredientsUseCase {
    suspend operator fun invoke(): List<RecipeDraftIngredient>
}
