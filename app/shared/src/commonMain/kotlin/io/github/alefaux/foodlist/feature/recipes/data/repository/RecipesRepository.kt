package io.github.alefaux.foodlist.feature.recipes.data.repository

import io.github.alefaux.foodlist.feature.recipes.domain.Recipe
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipeDetail(recipeId: Long): RecipeDetail?
}
