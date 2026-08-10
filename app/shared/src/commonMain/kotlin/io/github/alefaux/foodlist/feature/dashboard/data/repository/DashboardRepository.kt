package io.github.alefaux.foodlist.feature.dashboard.data.repository

import io.github.alefaux.foodlist.core.model.Product

interface DashboardRepository {
    suspend fun getProducts(): List<Product>
}