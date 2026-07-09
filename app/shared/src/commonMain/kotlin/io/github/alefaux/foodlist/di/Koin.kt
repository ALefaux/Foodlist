package io.github.alefaux.foodlist.di

import io.github.alefaux.foodlist.feature.dashboard.di.dashboardModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(dashboardModule)
        printLogger(Level.DEBUG)
    }
}