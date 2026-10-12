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
    data class ProductDetail(val productId: Int): FoodlistDestinations
    @Serializable
    object Login: FoodlistDestinations
    @Serializable
    object CreateAccount: FoodlistDestinations
    @Serializable
    object Recipes: FoodlistDestinations
    @Serializable
    data class RecipeDetail(val recipeId: Long): FoodlistDestinations
    @Serializable
    data class RecipeEdit(val recipeId: Long): FoodlistDestinations
    @Serializable
    object ShoppingList: FoodlistDestinations
    @Serializable
    data class ShoppingItemEdit(val itemId: Long): FoodlistDestinations
    @Serializable
    object Profile: FoodlistDestinations
}