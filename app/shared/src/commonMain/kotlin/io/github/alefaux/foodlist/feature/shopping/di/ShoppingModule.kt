package io.github.alefaux.foodlist.feature.shopping.di

import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepository
import io.github.alefaux.foodlist.feature.shopping.data.repository.ShoppingRepositoryImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.AddShoppingItemsUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.DeleteShoppingItemUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.GetShoppingItemDetailUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.GetShoppingItemsUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.SetShoppingItemPurchasedUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.data.usecase.UpdateShoppingItemUseCaseImpl
import io.github.alefaux.foodlist.feature.shopping.domain.AddShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.DeleteShoppingItemUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemDetailUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.GetShoppingItemsUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.SetShoppingItemPurchasedUseCase
import io.github.alefaux.foodlist.feature.shopping.domain.UpdateShoppingItemUseCase
import io.github.alefaux.foodlist.feature.shopping.presentation.ShoppingItemEditViewModel
import io.github.alefaux.foodlist.feature.shopping.presentation.ShoppingListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val shoppingModule = module {
    factory<ShoppingRepository> {
        ShoppingRepositoryImpl(
            shoppingItemDao = get(),
            productDao = get(),
            recipeDao = get()
        )
    }

    factory<GetShoppingItemsUseCase> {
        GetShoppingItemsUseCaseImpl(repository = get())
    }

    factory<AddShoppingItemsUseCase> {
        AddShoppingItemsUseCaseImpl(repository = get())
    }

    factory<SetShoppingItemPurchasedUseCase> {
        SetShoppingItemPurchasedUseCaseImpl(repository = get())
    }

    factory<GetShoppingItemDetailUseCase> {
        GetShoppingItemDetailUseCaseImpl(repository = get())
    }

    factory<UpdateShoppingItemUseCase> {
        UpdateShoppingItemUseCaseImpl(repository = get())
    }

    factory<DeleteShoppingItemUseCase> {
        DeleteShoppingItemUseCaseImpl(repository = get())
    }

    viewModel {
        ShoppingListViewModel(
            getShoppingItemsUseCase = get(),
            addShoppingItemsUseCase = get(),
            setShoppingItemPurchasedUseCase = get(),
            deleteShoppingItemUseCase = get(),
            getMissingRecipeIngredientsUseCase = get()
        )
    }

    viewModel { (itemId: Long) ->
        ShoppingItemEditViewModel(
            itemId = itemId,
            getShoppingItemDetailUseCase = get(),
            updateShoppingItemUseCase = get(),
            deleteShoppingItemUseCase = get(),
            getStorageUnitsUseCase = get()
        )
    }
}
