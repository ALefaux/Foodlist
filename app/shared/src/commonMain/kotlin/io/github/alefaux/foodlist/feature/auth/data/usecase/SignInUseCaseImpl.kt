package io.github.alefaux.foodlist.feature.auth.data.usecase

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import io.github.alefaux.foodlist.feature.auth.domain.SignInUseCase

class SignInUseCaseImpl(
    private val repository: AuthRepository
) : SignInUseCase {

    override suspend fun invoke(email: String, password: String): Result<AuthUser> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Please enter your email and password."))
        }

        return repository.signIn(email, password)
    }
}
