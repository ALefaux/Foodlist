package io.github.alefaux.foodlist.feature.sync.di

import io.github.alefaux.foodlist.feature.sync.data.repository.SyncRepository
import io.github.alefaux.foodlist.feature.sync.data.repository.SyncRepositoryImpl
import io.github.alefaux.foodlist.feature.sync.data.usecase.SyncLocalDataUseCaseImpl
import io.github.alefaux.foodlist.feature.sync.domain.SyncLocalDataUseCase
import org.koin.dsl.module

val syncModule = module {
    factory<SyncRepository> {
        SyncRepositoryImpl(
            httpClient = get(),
            productDao = get(),
            storageDao = get(),
            sessionDao = get()
        )
    }

    factory<SyncLocalDataUseCase> {
        SyncLocalDataUseCaseImpl(repository = get())
    }
}
