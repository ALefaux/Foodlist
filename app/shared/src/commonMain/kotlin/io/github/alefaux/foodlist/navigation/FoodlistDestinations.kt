package io.github.alefaux.foodlist.navigation

import kotlinx.serialization.Serializable

sealed interface FoodlistDestinations {
    @Serializable
    object Dashboard: FoodlistDestinations
    @Serializable
    object Add: FoodlistDestinations
    @Serializable
    object Scan: FoodlistDestinations
    @Serializable
    object Storage: FoodlistDestinations
}