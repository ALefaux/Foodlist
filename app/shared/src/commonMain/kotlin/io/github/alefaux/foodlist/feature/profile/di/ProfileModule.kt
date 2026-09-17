package io.github.alefaux.foodlist.feature.profile.di

import io.github.alefaux.foodlist.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel {
        ProfileViewModel(
            observeCurrentUserUseCase = get(),
            signOutUseCase = get(),
            themeRepository = get()
        )
    }
}
