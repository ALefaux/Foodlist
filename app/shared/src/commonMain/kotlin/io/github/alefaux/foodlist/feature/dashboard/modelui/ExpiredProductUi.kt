package io.github.alefaux.foodlist.feature.dashboard.modelui

data class ExpiredProductUi(
    val id: Int,
    val name: String,
    val stockageName: String,
    val expiredSince: String
)
