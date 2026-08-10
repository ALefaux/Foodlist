package io.github.alefaux.foodlist.core.logging

import platform.Foundation.NSLog

actual object AppLogging {
    actual fun e(throwable: Throwable, message: String) {
        NSLog("ERROR: $message\n${throwable.stackTraceToString()}")
    }

    actual fun d(message: String) {
        NSLog("DEBUG: $message")
    }
}
