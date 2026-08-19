package io.github.alefaux.foodlist.di

import io.github.alefaux.foodlist.feature.auth.di.authModule
import io.github.alefaux.foodlist.feature.dashboard.di.dashboardModule
import io.github.alefaux.foodlist.feature.productdetail.di.productDetailModule
import io.github.alefaux.foodlist.feature.profile.di.profileModule
import io.github.alefaux.foodlist.feature.scan.di.scanModule
import io.github.alefaux.foodlist.feature.storage.di.storageModule
import io.github.alefaux.foodlist.feature.sync.di.syncModule
import io.github.alefaux.foodlist.platformModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(
            authModule,
            dashboardModule,
            productDetailModule,
            profileModule,
            scanModule,
            storageModule,
            syncModule,
            platformModule(),
            databaseModule,
            networkModule
        )
        printLogger(Level.DEBUG)
    }
}