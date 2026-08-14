package io.github.alefaux.foodlist.feature.auth.di

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepositoryImpl
import io.github.alefaux.foodlist.feature.auth.data.usecase.ObserveCurrentUserUseCaseImpl
import io.github.alefaux.foodlist.feature.auth.data.usecase.SignInUseCaseImpl
import io.github.alefaux.foodlist.feature.auth.data.usecase.SignOutUseCaseImpl
import io.github.alefaux.foodlist.feature.auth.data.usecase.SignUpUseCaseImpl
import io.github.alefaux.foodlist.feature.auth.domain.ObserveCurrentUserUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignInUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignOutUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignUpUseCase
import io.github.alefaux.foodlist.feature.auth.presentation.AuthViewModel
import io.github.alefaux.foodlist.feature.sync.domain.SyncLocalDataUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory<AuthRepository> {
        AuthRepositoryImpl(
            httpClient = get(),
            sessionDao = get(),
            productDao = get(),
            storageDao = get()
        )
    }

    factory<SignInUseCase> {
        SignInUseCaseImpl(repository = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCaseImpl(repository = get())
    }

    factory<SignOutUseCase> {
        SignOutUseCaseImpl(repository = get())
    }

    factory<ObserveCurrentUserUseCase> {
        ObserveCurrentUserUseCaseImpl(repository = get())
    }

    viewModel {
        AuthViewModel(
            signInUseCase = get(),
            signUpUseCase = get(),
            syncLocalDataUseCase = get<SyncLocalDataUseCase>()
        )
    }
}
