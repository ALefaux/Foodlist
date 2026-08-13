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
    @Serializable
    data class StorageDetail(val storageId: Long): FoodlistDestinations
    @Serializable
    object Login: FoodlistDestinations
    @Serializable
    object CreateAccount: FoodlistDestinations
    @Serializable
    object Profile: FoodlistDestinations
}