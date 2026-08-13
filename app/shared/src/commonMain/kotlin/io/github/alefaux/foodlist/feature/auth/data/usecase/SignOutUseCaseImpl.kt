package io.github.alefaux.foodlist.feature.auth.data.usecase

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.domain.SignOutUseCase

class SignOutUseCaseImpl(
    private val repository: AuthRepository
) : SignOutUseCase {
    override suspend fun invoke() {
        repository.signOut()
    }
}
