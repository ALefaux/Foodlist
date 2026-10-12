package io.github.alefaux.foodlist.feature.recipes.data.repository

import io.github.alefaux.foodlist.feature.recipes.domain.Recipe
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraft
import io.github.alefaux.foodlist.feature.recipes.domain.MissingIngredient

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipeDetail(recipeId: Long): RecipeDetail?
    suspend fun updateRecipe(recipeId: Long, draft: RecipeDraft)
    suspend fun deleteRecipe(recipeId: Long)
    suspend fun getMissingIngredients(): List<MissingIngredient>
}
