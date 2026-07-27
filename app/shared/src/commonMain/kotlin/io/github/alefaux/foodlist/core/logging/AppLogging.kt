package io.github.alefaux.foodlist.core.logging

expect object AppLogging {
    fun e(throwable: Throwable, message: String)
    fun d(message: String)
}