package io.github.alefaux.foodlist.feature.dashboard.modelui

sealed class DiscardedProducts(
    open val discardedProductsCount: Int,
    open val trendPercent: Int
) {
    data class Positive(
        override val discardedProductsCount: Int,
        override val trendPercent: Int
    ): DiscardedProducts(discardedProductsCount, trendPercent)

    data class Negative(
        override val discardedProductsCount: Int,
        override val trendPercent: Int
    ): DiscardedProducts(discardedProductsCount, trendPercent)
}