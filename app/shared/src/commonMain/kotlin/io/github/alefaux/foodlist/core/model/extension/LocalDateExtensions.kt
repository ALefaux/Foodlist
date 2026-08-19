package io.github.alefaux.foodlist.core.model.extension

import io.github.alefaux.foodlist.core.model.ProductFreshness
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

fun LocalDate?.toFreshness(
    today: LocalDate,
    expiringSoonThresholdDays: Int = 3
): ProductFreshness {
    if (this == null) return ProductFreshness.FRESH

    val daysUntilExpiry = today.daysUntil(this)
    return when {
        daysUntilExpiry < 0 -> ProductFreshness.EXPIRED
        daysUntilExpiry <= expiringSoonThresholdDays -> ProductFreshness.EXPIRING_SOON
        else -> ProductFreshness.FRESH
    }
}

private val MONTH_ABBREVIATIONS = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

fun LocalDate.toDisplayString(): String =
    "${MONTH_ABBREVIATIONS[this.month.ordinal]} ${this.day}, ${this.year}"
