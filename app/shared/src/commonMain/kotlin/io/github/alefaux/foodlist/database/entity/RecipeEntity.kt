package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    val prepMinutes: Int = 0,
    val cookMinutes: Int,
    val servings: Int? = null,
    val calories: Int? = null,
    val isVegetarian: Boolean = false,
    val tip: String? = null,
    val isTestData: Boolean = false
)
