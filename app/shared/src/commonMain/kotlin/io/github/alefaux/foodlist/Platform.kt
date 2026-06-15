package io.github.alefaux.foodlist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform