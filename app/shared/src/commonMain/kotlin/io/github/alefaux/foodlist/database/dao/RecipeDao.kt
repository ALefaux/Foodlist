package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.RecipeEntity
import io.github.alefaux.foodlist.database.entity.RecipeIngredientEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM RecipeEntity ORDER BY name")
    suspend fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM RecipeIngredientEntity")
    suspend fun getAllIngredients(): List<RecipeIngredientEntity>

    @Insert
    suspend fun insert(recipe: RecipeEntity): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<RecipeIngredientEntity>)

    @Query("DELETE FROM RecipeIngredientEntity WHERE recipeId IN (SELECT id FROM RecipeEntity WHERE isTestData = 1)")
    suspend fun deleteTestIngredients()

    @Query("DELETE FROM RecipeEntity WHERE isTestData = 1")
    suspend fun deleteTestRecipes()
}
