package io.github.alefaux.foodlist.feature.recipes.domain

/** A recipe ingredient the pantry can't provide, with the first recipe needing it. */
data class MissingIngredient(
    val name: String,
    val quantity: String,
    val recipeId: Long
)
