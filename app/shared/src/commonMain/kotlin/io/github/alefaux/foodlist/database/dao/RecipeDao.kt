package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Query("DELETE FROM RecipeIngredientEntity WHERE recipeId IN (SELECT id FROM RecipeEntity WHERE isTestData = 1)")
    suspend fun deleteTestIngredients()

    @Query("DELETE FROM RecipeStepEntity WHERE recipeId IN (SELECT id FROM RecipeEntity WHERE isTestData = 1)")
    suspend fun deleteTestSteps()

    @Query("DELETE FROM RecipeEntity WHERE isTestData = 1")
    suspend fun deleteTestRecipes()
}
