package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StorageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
