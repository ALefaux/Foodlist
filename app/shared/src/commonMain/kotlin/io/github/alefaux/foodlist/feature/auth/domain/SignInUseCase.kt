package io.github.alefaux.foodlist.feature.auth.domain

interface SignInUseCase {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser>
}
