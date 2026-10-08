package io.github.alefaux.foodlist.feature.recipes.domain

/** A recipe as written by the user, before it is saved. */
data class RecipeDraft(
    val name: String,
    val description: String,
    val prepMinutes: Int,
    val cookMinutes: Int,
    val servings: Int?,
    val calories: Int?,
    val isVegetarian: Boolean,
    val tip: String?,
    val ingredients: List<RecipeDraftIngredient>,
    val steps: List<RecipeDraftStep>
)

data class RecipeDraftIngredient(
    val name: String,
    val quantity: String
)

data class RecipeDraftStep(
    val title: String,
    val instructions: String,
    val timerMinutes: Int?
)
