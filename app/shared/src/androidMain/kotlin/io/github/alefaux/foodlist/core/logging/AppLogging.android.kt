package io.github.alefaux.foodlist.core.logging

import timber.log.Timber

actual object AppLogging {
    actual fun e(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
    }

    actual fun d(message: String) {
        Timber.d(message)
    }
}