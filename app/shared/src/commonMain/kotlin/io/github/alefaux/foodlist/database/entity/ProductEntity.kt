package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expirationDate: Instant?,
    val ean: String,
    val discardedDate: Instant?,
    val storageId: Long? = null,
    val quantity: String = "",
    val category: String = "Other"
)
