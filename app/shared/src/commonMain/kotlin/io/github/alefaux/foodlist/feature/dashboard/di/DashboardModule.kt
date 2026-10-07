package io.github.alefaux.foodlist.feature.dashboard.di

import io.github.alefaux.foodlist.feature.dashboard.data.repository.DashboardRepository
import io.github.alefaux.foodlist.feature.dashboard.data.repository.DashboardRepositoryImpl
import io.github.alefaux.foodlist.feature.dashboard.data.usecase.GetDiscardedProductsStatsUseCaseImpl
import io.github.alefaux.foodlist.feature.dashboard.data.usecase.GetExpiredProductsUseCaseImpl
import io.github.alefaux.foodlist.feature.dashboard.domain.GetDiscardedProductsStatsUseCase
import io.github.alefaux.foodlist.feature.dashboard.domain.GetExpiredProductsUseCase
import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    factory<DashboardRepository> {
        DashboardRepositoryImpl(
            productDao = get()
        )
    }
    
    factory<GetExpiredProductsUseCase> {
        GetExpiredProductsUseCaseImpl(
            repository = get()
        )
    }

    factory<GetDiscardedProductsStatsUseCase> {
        GetDiscardedProductsStatsUseCaseImpl(
            repository = get()
        )
    }

    viewModel {
        DashboardViewModel(
            getExpiredProductsUseCase = get(),
            getDiscardedProductsStatsUseCase = get(),
            httpClient = get(),
            appBuildInfo = get()
        )
    }
}