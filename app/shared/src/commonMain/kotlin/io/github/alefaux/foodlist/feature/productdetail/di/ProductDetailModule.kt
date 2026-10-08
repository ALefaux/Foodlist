package io.github.alefaux.foodlist.feature.productdetail.di

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepositoryImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.DeleteProductUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.DiscardProductUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.GetProductDetailUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.MoveProductUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.UpdateProductStockUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.data.usecase.UpdateProductUseCaseImpl
import io.github.alefaux.foodlist.feature.productdetail.domain.DeleteProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.DiscardProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.GetProductDetailUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.MoveProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductStockUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.presentation.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productDetailModule = module {
    factory<ProductDetailRepository> {
        ProductDetailRepositoryImpl(
            productDao = get(),
            storageDao = get()
        )
    }

    factory<GetProductDetailUseCase> {
        GetProductDetailUseCaseImpl(repository = get())
    }

    factory<UpdateProductStockUseCase> {
        UpdateProductStockUseCaseImpl(repository = get())
    }

    factory<UpdateProductUseCase> {
        UpdateProductUseCaseImpl(repository = get())
    }

    factory<MoveProductUseCase> {
        MoveProductUseCaseImpl(repository = get())
    }

    factory<DeleteProductUseCase> {
        DeleteProductUseCaseImpl(repository = get())
    }

    factory<DiscardProductUseCase> {
        DiscardProductUseCaseImpl(repository = get())
    }

    viewModel { (productId: Int) ->
        ProductDetailViewModel(
            productId = productId,
            getProductDetailUseCase = get(),
            updateProductStockUseCase = get(),
            updateProductUseCase = get(),
            moveProductUseCase = get(),
            deleteProductUseCase = get(),
            discardProductUseCase = get(),
            getStorageUnitsUseCase = get()
        )
    }
}
