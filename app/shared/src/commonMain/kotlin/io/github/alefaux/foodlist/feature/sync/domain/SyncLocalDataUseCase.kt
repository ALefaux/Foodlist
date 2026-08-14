package io.github.alefaux.foodlist.feature.sync.domain

interface SyncLocalDataUseCase {
    suspend operator fun invoke(): Result<Unit>
}
