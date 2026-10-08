package io.github.alefaux.foodlist.feature.recipes.domain

interface GetRecipesUseCase {
    suspend operator fun invoke(): List<Recipe>
}
