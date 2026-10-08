package io.github.alefaux.foodlist.feature.recipes.modelui

/** An ingredient row of the edit form. [key] stays stable while the row is edited, added or removed. */
data class EditableIngredient(
    val key: Long,
    val name: String,
    val quantity: String
)

/** A step card of the edit form. [timerMinutes] is the raw text typed, empty for no timer. */
data class EditableStep(
    val key: Long,
    val title: String,
    val instructions: String,
    val timerMinutes: String
)
