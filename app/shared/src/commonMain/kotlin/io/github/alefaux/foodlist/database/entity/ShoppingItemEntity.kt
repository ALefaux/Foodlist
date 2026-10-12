package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    /** Size of one package, e.g. "1 L". */
    val quantity: String = "",
    /** Number of packages to buy. */
    val count: Int = 1,
    val category: String = "Other",
    /** Storage the item goes to once purchased, null to not add it to the pantry. */
    val storageId: Long? = null,
    /** Name of a ShoppingPriority. */
    val priority: String = "STANDARD",
    val brands: String = "",
    val note: String = "",
    /** Recipe the item was suggested for. */
    val recipeId: Long? = null,
    /** Pantry product created when the item was purchased, removed if the purchase is undone. */
    val productId: Int? = null,
    /** When the item was ticked as bought, null while it is still to buy. */
    val purchasedAt: Instant? = null,
    val createdAt: Instant,
    val isTestData: Boolean = false
)
