package io.github.alefaux.foodlist.feature.recipes.domain

data class RecipeDetail(
    val id: Long,
    val name: String,
    val description: String,
    val prepMinutes: Int,
    val cookMinutes: Int,
    val servings: Int?,
    val calories: Int?,
    val isVegetarian: Boolean,
    val tip: String?,
    val availability: RecipeAvailability,
    val ingredients: List<RecipeIngredient>,
    val steps: List<RecipeStep>
)

data class RecipeIngredient(
    val name: String,
    val quantity: String,
    val status: IngredientStatus,
    /** Where the matching pantry product is stored, when there is one. */
    val storageName: String?
)

enum class IngredientStatus {
    IN_PANTRY,
    EXPIRING_SOON,
    MISSING
}

data class RecipeStep(
    val position: Int,
    val title: String,
    val instructions: String,
    val timerMinutes: Int?
)
