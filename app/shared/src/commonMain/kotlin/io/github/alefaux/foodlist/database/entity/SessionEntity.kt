package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Single-row table; id is always SINGLETON_ID, userId is null when signed out.
@Entity
data class SessionEntity(
    @PrimaryKey val id: Int = SINGLETON_ID,
    val userId: Long?
) {
    companion object {
        const val SINGLETON_ID = 0
    }
}
