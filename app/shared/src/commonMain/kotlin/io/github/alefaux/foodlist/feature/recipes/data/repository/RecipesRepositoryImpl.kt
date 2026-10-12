package io.github.alefaux.foodlist.feature.recipes.data.repository

import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.RecipeDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.RecipeIngredientEntity
import io.github.alefaux.foodlist.database.entity.RecipeStepEntity
import io.github.alefaux.foodlist.feature.recipes.domain.IngredientStatus
import io.github.alefaux.foodlist.feature.recipes.domain.Recipe
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraft
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDraftIngredient
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeIngredient
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeStep
import kotlin.time.Clock

class RecipesRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val productDao: ProductDao,
    private val storageDao: StorageDao
) : RecipesRepository {

    override suspend fun getRecipes(): List<Recipe> {
        val pantry = loadPantry()
        val ingredientsByRecipe = recipeDao.getAllIngredients().groupBy({ it.recipeId }, { it.name })

        return recipeDao.getAll().map { recipe ->
            val ingredients = ingredientsByRecipe[recipe.id].orEmpty()

            Recipe(
                id = recipe.id,
                name = recipe.name,
                durationMinutes = recipe.prepMinutes + recipe.cookMinutes,
                ingredients = ingredients,
                availability = ingredients.map { pantry.find(it).status }.toAvailability()
            )
        }
    }

    override suspend fun getRecipeDetail(recipeId: Long): RecipeDetail? {
        val recipe = recipeDao.getById(recipeId) ?: return null
        val pantry = loadPantry()
        val storageNames = storageDao.getAll().associate { it.id to it.name }

        val ingredients = recipeDao.getIngredients(recipeId).map { ingredient ->
            val product = pantry.find(ingredient.name)
            RecipeIngredient(
                name = ingredient.name,
                quantity = ingredient.quantity,
                status = product.status,
                storageName = product?.storageId?.let { storageNames[it] }
            )
        }

        return RecipeDetail(
            id = recipe.id,
            name = recipe.name,
            description = recipe.description,
            prepMinutes = recipe.prepMinutes,
            cookMinutes = recipe.cookMinutes,
            servings = recipe.servings,
            calories = recipe.calories,
            isVegetarian = recipe.isVegetarian,
            tip = recipe.tip,
            availability = ingredients.map { it.status }.toAvailability(),
            ingredients = ingredients,
            steps = recipeDao.getSteps(recipeId).map {
                RecipeStep(
                    position = it.position,
                    title = it.title,
                    instructions = it.instructions,
                    timerMinutes = it.timerMinutes
                )
            }
        )
    }

    override suspend fun updateRecipe(recipeId: Long, draft: RecipeDraft) {
        val recipe = recipeDao.getById(recipeId) ?: return

        recipeDao.replace(
            recipe = recipe.copy(
                name = draft.name,
                description = draft.description,
                prepMinutes = draft.prepMinutes,
                cookMinutes = draft.cookMinutes,
                servings = draft.servings,
                calories = draft.calories,
                isVegetarian = draft.isVegetarian,
                tip = draft.tip
            ),
            ingredients = draft.ingredients.map {
                RecipeIngredientEntity(recipeId = recipeId, name = it.name, quantity = it.quantity)
            },
            steps = draft.steps.mapIndexed { index, step ->
                RecipeStepEntity(
                    recipeId = recipeId,
                    position = index + 1,
                    title = step.title,
                    instructions = step.instructions,
                    timerMinutes = step.timerMinutes
                )
            }
        )
    }

    override suspend fun deleteRecipe(recipeId: Long) {
        recipeDao.deleteRecipe(recipeId)
    }

    override suspend fun getMissingIngredients(): List<RecipeDraftIngredient> {
        val pantry = loadPantry()

        return recipeDao.getAllIngredients()
            .filter { pantry.find(it.name) == null }
            .distinctBy { it.name.trim().lowercase() }
            .map { RecipeDraftIngredient(name = it.name, quantity = it.quantity) }
    }

    /** Usable pantry products: not discarded and not expired yet. */
    private suspend fun loadPantry(): List<PantryProduct> {
        val today = Clock.System.now().toLocalDate()

        return productDao.getAll()
            .filter { it.discardedDate == null }
            .map {
                PantryProduct(
                    name = it.name.lowercase(),
                    freshness = it.expirationDate?.toLocalDate().toFreshness(today),
                    storageId = it.storageId
                )
            }
            .filter { it.freshness != ProductFreshness.EXPIRED }
    }

    /** Best pantry product for an ingredient: a fresh one when possible. */
    private fun List<PantryProduct>.find(ingredient: String): PantryProduct? {
        val name = ingredient.lowercase()
        val matches = filter { it.name.contains(name) || name.contains(it.name) }
        return matches.firstOrNull { it.freshness == ProductFreshness.FRESH } ?: matches.firstOrNull()
    }

    private val PantryProduct?.status: IngredientStatus
        get() = when {
            this == null -> IngredientStatus.MISSING
            freshness == ProductFreshness.FRESH -> IngredientStatus.IN_PANTRY
            else -> IngredientStatus.EXPIRING_SOON
        }

    private fun List<IngredientStatus>.toAvailability(): RecipeAvailability = when {
        IngredientStatus.MISSING in this -> RecipeAvailability.MISSING_INGREDIENTS
        IngredientStatus.EXPIRING_SOON in this -> RecipeAvailability.USES_EXPIRING_ITEMS
        else -> RecipeAvailability.CAN_MAKE
    }

    private data class PantryProduct(
        val name: String,
        val freshness: ProductFreshness,
        val storageId: Long?
    )
}
