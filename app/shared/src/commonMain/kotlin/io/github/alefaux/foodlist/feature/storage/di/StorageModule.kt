package io.github.alefaux.foodlist.feature.storage.di

import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepository
import io.github.alefaux.foodlist.feature.storage.data.repository.StorageRepositoryImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.AddStorageUnitUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.data.usecase.GetStorageUnitsUseCaseImpl
import io.github.alefaux.foodlist.feature.storage.domain.AddStorageUnitUseCase
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import io.github.alefaux.foodlist.feature.storage.presentation.StorageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val storageModule = module {
    factory<StorageRepository> {
        StorageRepositoryImpl(
            storageDao = get()
        )
    }

    factory<GetStorageUnitsUseCase> {
        GetStorageUnitsUseCaseImpl(repository = get())
    }

    factory<AddStorageUnitUseCase> {
        AddStorageUnitUseCaseImpl(repository = get())
    }

    viewModel {
        StorageViewModel(
            getStorageUnitsUseCase = get(),
            addStorageUnitUseCase = get()
        )
    }
}
