package io.github.alefaux.foodlist.feature.auth.domain

interface SignUpUseCase {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<AuthUser>
}
