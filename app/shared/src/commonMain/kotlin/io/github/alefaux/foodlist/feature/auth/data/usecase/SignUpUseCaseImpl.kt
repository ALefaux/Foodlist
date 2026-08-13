package io.github.alefaux.foodlist.feature.auth.data.usecase

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import io.github.alefaux.foodlist.feature.auth.domain.SignUpUseCase

class SignUpUseCaseImpl(
    private val repository: AuthRepository
) : SignUpUseCase {

    override suspend fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<AuthUser> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("Please enter your name."))
        }
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Please enter your email."))
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return Result.failure(IllegalArgumentException("Password must be at least $MIN_PASSWORD_LENGTH characters."))
        }
        if (password != confirmPassword) {
            return Result.failure(IllegalArgumentException("Passwords do not match."))
        }

        return repository.signUp(name, email, password)
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}
