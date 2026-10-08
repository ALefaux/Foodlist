package io.github.alefaux.foodlist.feature.recipes.data.repository

import io.github.alefaux.foodlist.feature.recipes.domain.Recipe

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>
}
