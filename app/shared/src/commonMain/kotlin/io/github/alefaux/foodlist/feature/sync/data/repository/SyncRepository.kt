package io.github.alefaux.foodlist.feature.sync.data.repository

interface SyncRepository {
    suspend fun syncLocalDataToServer(): Result<Unit>
}
