package io.github.alefaux.foodlist.feature.recipes.di

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepositoryImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.GetRecipeDetailUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.GetRecipesUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipeDetailUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipesUseCase
import io.github.alefaux.foodlist.feature.recipes.presentation.RecipeDetailViewModel
import io.github.alefaux.foodlist.feature.recipes.presentation.RecipesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipesModule = module {
    factory<RecipesRepository> {
        RecipesRepositoryImpl(
            recipeDao = get(),
            productDao = get(),
            storageDao = get()
        )
    }

    factory<GetRecipesUseCase> {
        GetRecipesUseCaseImpl(repository = get())
    }

    factory<GetRecipeDetailUseCase> {
        GetRecipeDetailUseCaseImpl(repository = get())
    }

    viewModel {
        RecipesViewModel(
            getRecipesUseCase = get()
        )
    }

    viewModel { (recipeId: Long) ->
        RecipeDetailViewModel(
            recipeId = recipeId,
            getRecipeDetailUseCase = get()
        )
    }
}
