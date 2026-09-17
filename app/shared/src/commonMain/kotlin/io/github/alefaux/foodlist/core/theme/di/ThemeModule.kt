package io.github.alefaux.foodlist.core.theme.di

import io.github.alefaux.foodlist.core.theme.ThemeRepository
import org.koin.dsl.module

val themeModule = module {
    single { ThemeRepository(storage = get()) }
}
