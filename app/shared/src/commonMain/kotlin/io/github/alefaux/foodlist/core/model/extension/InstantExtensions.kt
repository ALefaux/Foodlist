package io.github.alefaux.foodlist.core.model.extension

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Instant.toLocalDate(): LocalDate =
    this.toLocalDateTime(TimeZone.currentSystemDefault()).date