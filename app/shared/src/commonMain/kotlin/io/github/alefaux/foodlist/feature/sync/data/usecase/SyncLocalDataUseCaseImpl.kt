package io.github.alefaux.foodlist.feature.sync.data.usecase

import io.github.alefaux.foodlist.feature.sync.data.repository.SyncRepository
import io.github.alefaux.foodlist.feature.sync.domain.SyncLocalDataUseCase

class SyncLocalDataUseCaseImpl(
    private val repository: SyncRepository
) : SyncLocalDataUseCase {
    override suspend fun invoke(): Result<Unit> = repository.syncLocalDataToServer()
}
