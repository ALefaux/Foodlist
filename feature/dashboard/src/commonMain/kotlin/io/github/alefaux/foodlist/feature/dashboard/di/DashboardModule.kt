package io.github.alefaux.foodlist.feature.dashboard.di

import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { DashboardViewModel() }
}