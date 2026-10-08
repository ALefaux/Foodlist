package io.github.alefaux.foodlist.core.logging

import timber.log.Timber

actual object AppLogging {
    actual fun e(throwable: Throwable, message: String) {
        Timber.tag(callerTag()).e(throwable, message)
    }

    actual fun d(message: String) {
        Timber.tag(callerTag()).d(message)
    }

    // Timber would otherwise tag every log "AppLogging"; use the calling class instead.
    private fun callerTag(): String {
        val caller = Throwable().stackTrace.firstOrNull { it.className != AppLogging::class.java.name }
            ?: return "AppLogging"
        return caller.className.substringAfterLast('.').substringBefore('$')
    }
}
