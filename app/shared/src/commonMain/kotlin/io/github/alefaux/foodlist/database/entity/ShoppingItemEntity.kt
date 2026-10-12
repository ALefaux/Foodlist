package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantity: String = "",
    /** When the item was ticked as bought, null while it is still to buy. */
    val purchasedAt: Instant? = null,
    val createdAt: Instant,
    val isTestData: Boolean = false
)
