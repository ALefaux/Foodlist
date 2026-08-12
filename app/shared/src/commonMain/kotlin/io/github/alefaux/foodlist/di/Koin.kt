package io.github.alefaux.foodlist.di

import io.github.alefaux.foodlist.feature.dashboard.di.dashboardModule
import io.github.alefaux.foodlist.feature.scan.di.scanModule
import io.github.alefaux.foodlist.feature.storage.di.storageModule
import io.github.alefaux.foodlist.platformModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(
            dashboardModule,
            scanModule,
            storageModule,
            platformModule(),
            databaseModule,
            networkModule
        )
        printLogger(Level.DEBUG)
    }
}