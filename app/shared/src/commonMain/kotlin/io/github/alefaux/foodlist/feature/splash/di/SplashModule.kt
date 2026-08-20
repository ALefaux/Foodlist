package io.github.alefaux.foodlist.feature.splash.di

import io.github.alefaux.foodlist.feature.splash.presentation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel {
        SplashViewModel(httpClient = get())
    }
}
