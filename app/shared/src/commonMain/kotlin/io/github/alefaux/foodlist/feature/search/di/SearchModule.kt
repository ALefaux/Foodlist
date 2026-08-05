package io.github.alefaux.foodlist.feature.search.di

import io.github.alefaux.foodlist.feature.search.data.repository.SearchRepository
import io.github.alefaux.foodlist.feature.search.data.repository.SearchRepositoryImpl
import io.github.alefaux.foodlist.feature.search.data.service.SearchService
import io.github.alefaux.foodlist.feature.search.data.usecase.SearchProductsUseCaseImpl
import io.github.alefaux.foodlist.feature.search.domain.SearchProductsUseCase
import io.github.alefaux.foodlist.feature.search.presentation.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    factory {
        SearchService(
            httpClient = get()
        )
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(
            service = get()
        )
    }

    factory<SearchProductsUseCase> {
        SearchProductsUseCaseImpl(
            searchRepository = get()
        )
    }

    viewModel {
        SearchViewModel(
            searchProductsUseCase = get()
        )
    }

}