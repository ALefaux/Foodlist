package io.github.alefaux.foodlist.di

import io.github.alefaux.foodlist.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    factory { get<AppDatabase>().productDao() }
}