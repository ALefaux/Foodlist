package io.github.alefaux.foodlist.feature.storage.di

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepositoryImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.AddStorageUnitUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.DeleteStorageUnitUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.GetStorageDetailUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.GetStorageUnitsUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.RestoreDiscardedProductUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.domain.AddStorageUnitUseCase
import io.github.alefaux.foodlist.feature.storage.domain.DeleteStorageUnitUseCase
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageDetailUseCase
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import io.github.alefaux.foodlist.feature.storage.domain.RestoreDiscardedProductUseCase
import io.github.alefaux.foodlist.feature.storage.presentation.StorageDetailViewModel
import io.github.alefaux.foodlist.feature.storage.presentation.StorageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val storageModule = module {
    factory<StorageRepository> {
        StorageRepositoryImpl(
            storageDao = get(),
            productDao = get()
        )
    }

    factory<GetStorageUnitsUseCase> {
        GetStorageUnitsUseCaseImpl(repository = get())
    }

    factory<AddStorageUnitUseCase> {
        AddStorageUnitUseCaseImpl(repository = get())
    }

    factory<GetStorageDetailUseCase> {
        GetStorageDetailUseCaseImpl(repository = get())
    }

    factory<DeleteStorageUnitUseCase> {
        DeleteStorageUnitUseCaseImpl(repository = get())
    }

    factory<RestoreDiscardedProductUseCase> {
        RestoreDiscardedProductUseCaseImpl(repository = get())
    }

    viewModel {
        StorageViewModel(
            getStorageUnitsUseCase = get(),
            addStorageUnitUseCase = get()
        )
    }

    viewModel { (storageId: Long) ->
        StorageDetailViewModel(
            storageId = storageId,
            getStorageDetailUseCase = get(),
            deleteStorageUnitUseCase = get(),
            restoreDiscardedProductUseCase = get()
        )
    }
}
