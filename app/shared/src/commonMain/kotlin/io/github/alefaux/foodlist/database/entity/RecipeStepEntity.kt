package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("recipeId")])
data class RecipeStepEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val position: Int,
    val title: String,
    val instructions: String,
    val timerMinutes: Int? = null
)
