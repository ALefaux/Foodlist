package io.github.alefaux.foodlist

import io.github.alefaux.foodlist.core.auth.GoogleAuthProvider
import io.github.alefaux.foodlist.core.build.AppBuildInfo
import io.github.alefaux.foodlist.core.auth.IosGoogleAuthProvider
import io.github.alefaux.foodlist.core.network.IosNetworkConnectivityChecker
import io.github.alefaux.foodlist.core.network.NetworkConnectivityChecker
import io.github.alefaux.foodlist.core.theme.IosThemePreferencesStorage
import io.github.alefaux.foodlist.core.theme.ThemePreferencesStorage
import io.github.alefaux.foodlist.database.AppDatabase
import io.github.alefaux.foodlist.database.getDatabaseBuilder
import io.github.alefaux.foodlist.database.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalNativeApi::class)
actual fun platformModule(): Module = module {
    single {
        AppBuildInfo(isDebug = kotlin.native.Platform.isDebugBinary)
    }

    single<AppDatabase> {
        val builder = getDatabaseBuilder()
        getRoomDatabase(builder)
    }

    single<NetworkConnectivityChecker> {
        IosNetworkConnectivityChecker()
    }

    single<GoogleAuthProvider> {
        IosGoogleAuthProvider()
    }

    single<ThemePreferencesStorage> {
        IosThemePreferencesStorage()
    }
}