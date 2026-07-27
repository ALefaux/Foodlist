package io.github.alefaux.foodlist

import android.app.Application
import io.github.alefaux.foodlist.di.initKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class FoodlistApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@FoodlistApp)
        }

        Timber.plant(Timber.DebugTree())
    }
}