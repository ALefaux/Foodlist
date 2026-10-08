package io.github.alefaux.foodlist.feature.recipes.di

import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepository
import io.github.alefaux.foodlist.feature.recipes.data.repository.RecipesRepositoryImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.DeleteRecipeUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.GetRecipeDetailUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.GetRecipesUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.data.usecase.UpdateRecipeUseCaseImpl
import io.github.alefaux.foodlist.feature.recipes.domain.DeleteRecipeUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipeDetailUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.GetRecipesUseCase
import io.github.alefaux.foodlist.feature.recipes.domain.UpdateRecipeUseCase
import io.github.alefaux.foodlist.feature.recipes.presentation.RecipeDetailViewModel
import io.github.alefaux.foodlist.feature.recipes.presentation.RecipeEditViewModel
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

    factory<UpdateRecipeUseCase> {
        UpdateRecipeUseCaseImpl(repository = get())
    }

    factory<DeleteRecipeUseCase> {
        DeleteRecipeUseCaseImpl(repository = get())
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

    viewModel { (recipeId: Long) ->
        RecipeEditViewModel(
            recipeId = recipeId,
            getRecipeDetailUseCase = get(),
            updateRecipeUseCase = get(),
            deleteRecipeUseCase = get()
        )
    }
}
