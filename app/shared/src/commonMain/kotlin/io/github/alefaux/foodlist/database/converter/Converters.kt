package io.github.alefaux.foodlist.database.converter

import androidx.room.TypeConverter
import kotlin.time.Instant

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let { Instant.fromEpochMilliseconds(it) }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): Long? = instant?.toEpochMilliseconds()
}