package io.github.alefaux.foodlist.feature.recipes.data.repository

import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.RecipeDao
import io.github.alefaux.foodlist.feature.recipes.domain.Recipe
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import kotlin.time.Clock

class RecipesRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val productDao: ProductDao
) : RecipesRepository {

    override suspend fun getRecipes(): List<Recipe> {
        val today = Clock.System.now().toLocalDate()
        val ingredientsByRecipe = recipeDao.getAllIngredients().groupBy({ it.recipeId }, { it.name })

        // Usable pantry products: not discarded and not expired yet.
        val pantry = productDao.getAll()
            .filter { it.discardedDate == null }
            .map { it.name.lowercase() to it.expirationDate?.toLocalDate().toFreshness(today) }
            .filter { (_, freshness) -> freshness != ProductFreshness.EXPIRED }

        return recipeDao.getAll().map { recipe ->
            val ingredients = ingredientsByRecipe[recipe.id].orEmpty()
            val matches = ingredients.map { ingredient ->
                val name = ingredient.lowercase()
                pantry.filter { (productName, _) -> productName.contains(name) || name.contains(productName) }
            }

            Recipe(
                id = recipe.id,
                name = recipe.name,
                durationMinutes = recipe.durationMinutes,
                ingredients = ingredients,
                availability = when {
                    matches.any { it.isEmpty() } -> RecipeAvailability.MISSING_INGREDIENTS
                    matches.any { products -> products.none { (_, freshness) -> freshness == ProductFreshness.FRESH } } ->
                        RecipeAvailability.USES_EXPIRING_ITEMS
                    else -> RecipeAvailability.CAN_MAKE
                }
            )
        }
    }
}
