package io.github.alefaux.foodlist.feature.profile.di

import io.github.alefaux.foodlist.feature.profile.data.usecase.CreateTestDataUseCaseImpl
import io.github.alefaux.foodlist.feature.profile.data.usecase.DeleteTestDataUseCaseImpl
import io.github.alefaux.foodlist.feature.profile.domain.CreateTestDataUseCase
import io.github.alefaux.foodlist.feature.profile.domain.DeleteTestDataUseCase
import io.github.alefaux.foodlist.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    factory<CreateTestDataUseCase> {
        CreateTestDataUseCaseImpl(
            storageDao = get(),
            productDao = get()
        )
    }

    factory<DeleteTestDataUseCase> {
        DeleteTestDataUseCaseImpl(
            storageDao = get(),
            productDao = get()
        )
    }

    viewModel {
        ProfileViewModel(
            observeCurrentUserUseCase = get(),
            signOutUseCase = get(),
            createTestDataUseCase = get(),
            deleteTestDataUseCase = get(),
            themeRepository = get(),
            appBuildInfo = get()
        )
    }
}
