package io.github.alefaux.foodlist.feature.recipes.modelui

import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability

data class RecipeUi(
    val id: Long,
    val name: String,
    val ingredientsCount: Int,
    val durationMinutes: Int,
    val availability: RecipeAvailability
)
