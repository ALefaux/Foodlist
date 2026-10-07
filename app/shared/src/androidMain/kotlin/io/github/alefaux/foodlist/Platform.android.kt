package io.github.alefaux.foodlist

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import io.github.alefaux.foodlist.core.build.AppBuildInfo
import io.github.alefaux.foodlist.core.auth.AndroidGoogleAuthProvider
import io.github.alefaux.foodlist.core.auth.GoogleAuthProvider
import io.github.alefaux.foodlist.core.network.AndroidNetworkConnectivityChecker
import io.github.alefaux.foodlist.core.network.NetworkConnectivityChecker
import io.github.alefaux.foodlist.core.theme.AndroidThemePreferencesStorage
import io.github.alefaux.foodlist.core.theme.ThemePreferencesStorage
import io.github.alefaux.foodlist.database.getDatabaseBuilder
import io.github.alefaux.foodlist.database.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformModule(): Module = module {
    single {
        val flags = get<Context>().applicationInfo.flags
        AppBuildInfo(isDebug = flags and ApplicationInfo.FLAG_DEBUGGABLE != 0)
    }

    single {
        val builder = getDatabaseBuilder(context = get())
        getRoomDatabase(builder)
    }

    single<NetworkConnectivityChecker> {
        AndroidNetworkConnectivityChecker(context = get())
    }

    single<GoogleAuthProvider> {
        AndroidGoogleAuthProvider()
    }

    single<ThemePreferencesStorage> {
        AndroidThemePreferencesStorage(context = get())
    }
}