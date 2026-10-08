package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.github.alefaux.foodlist.database.entity.RecipeEntity
import io.github.alefaux.foodlist.database.entity.RecipeIngredientEntity
import io.github.alefaux.foodlist.database.entity.RecipeStepEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM RecipeEntity ORDER BY name")
    suspend fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    suspend fun getById(id: Long): RecipeEntity?

    @Query("SELECT * FROM RecipeIngredientEntity")
    suspend fun getAllIngredients(): List<RecipeIngredientEntity>

    @Query("SELECT * FROM RecipeIngredientEntity WHERE recipeId = :recipeId ORDER BY id")
    suspend fun getIngredients(recipeId: Long): List<RecipeIngredientEntity>

    @Query("SELECT * FROM RecipeStepEntity WHERE recipeId = :recipeId ORDER BY position")
    suspend fun getSteps(recipeId: Long): List<RecipeStepEntity>

    @Insert
    suspend fun insert(recipe: RecipeEntity): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<RecipeIngredientEntity>)

    @Insert
    suspend fun insertSteps(steps: List<RecipeStepEntity>)

    @Update
    suspend fun update(recipe: RecipeEntity)

    @Query("DELETE FROM RecipeIngredientEntity WHERE recipeId = :recipeId")
    suspend fun deleteIngredients(recipeId: Long)

    @Query("DELETE FROM RecipeStepEntity WHERE recipeId = :recipeId")
    suspend fun deleteSteps(recipeId: Long)

    @Query("DELETE FROM RecipeEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    /** Saves an edited recipe, replacing all of its ingredients and steps. */
    @Transaction
    suspend fun replace(
        recipe: RecipeEntity,
        ingredients: List<RecipeIngredientEntity>,
        steps: List<RecipeStepEntity>
    ) {
        update(recipe)
        deleteIngredients(recipe.id)
        insertIngredients(ingredients)
        deleteSteps(recipe.id)
        insertSteps(steps)
    }

    @Transaction
    suspend fun deleteRecipe(id: Long) {
        deleteIngredients(id)
        deleteSteps(id)
        deleteById(id)
    }

    @Query("DELETE FROM RecipeIngredientEntity WHERE recipeId IN (SELECT id FROM RecipeEntity WHERE isTestData = 1)")
    suspend fun deleteTestIngredients()

    @Query("DELETE FROM RecipeStepEntity WHERE recipeId IN (SELECT id FROM RecipeEntity WHERE isTestData = 1)")
    suspend fun deleteTestSteps()

    @Query("DELETE FROM RecipeEntity WHERE isTestData = 1")
    suspend fun deleteTestRecipes()
}
