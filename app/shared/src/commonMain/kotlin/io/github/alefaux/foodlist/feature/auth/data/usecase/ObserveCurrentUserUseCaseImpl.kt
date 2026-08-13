package io.github.alefaux.foodlist.feature.auth.data.usecase

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import io.github.alefaux.foodlist.feature.auth.domain.ObserveCurrentUserUseCase
import kotlinx.coroutines.flow.Flow

class ObserveCurrentUserUseCaseImpl(
    private val repository: AuthRepository
) : ObserveCurrentUserUseCase {
    override fun invoke(): Flow<AuthUser?> = repository.observeCurrentUser()
}
