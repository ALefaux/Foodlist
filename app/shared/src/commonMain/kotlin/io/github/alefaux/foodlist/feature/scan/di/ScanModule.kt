package io.github.alefaux.foodlist.feature.scan.di

import io.github.alefaux.foodlist.feature.scan.data.repository.ScanRepository
import io.github.alefaux.foodlist.feature.scan.data.repository.ScanRepositoryImpl
import io.github.alefaux.foodlist.feature.scan.data.usecase.AddScannedProductUseCaseImpl
import io.github.alefaux.foodlist.feature.scan.data.usecase.LookupProductUseCaseImpl
import io.github.alefaux.foodlist.feature.scan.domain.AddScannedProductUseCase
import io.github.alefaux.foodlist.feature.scan.domain.LookupProductUseCase
import io.github.alefaux.foodlist.feature.scan.presentation.ScanViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val scanModule = module {
    factory<ScanRepository> {
        ScanRepositoryImpl(
            httpClient = get(),
            productDao = get()
        )
    }

    factory<LookupProductUseCase> {
        LookupProductUseCaseImpl(repository = get())
    }

    factory<AddScannedProductUseCase> {
        AddScannedProductUseCaseImpl(repository = get())
    }

    viewModel {
        ScanViewModel(
            networkConnectivityChecker = get(),
            lookupProductUseCase = get(),
            addScannedProductUseCase = get()
        )
    }
}
