package io.github.alefaux.foodlist.feature.recipes.domain

data class Recipe(
    val id: Long,
    val name: String,
    val durationMinutes: Int,
    val ingredients: List<String>,
    val availability: RecipeAvailability
)

enum class RecipeAvailability {
    /** Every ingredient is in the pantry and none of them expires soon. */
    CAN_MAKE,

    /** Every ingredient is in the pantry and at least one of them expires soon. */
    USES_EXPIRING_ITEMS,

    /** At least one ingredient is not in the pantry, or only expired or discarded. */
    MISSING_INGREDIENTS
}
