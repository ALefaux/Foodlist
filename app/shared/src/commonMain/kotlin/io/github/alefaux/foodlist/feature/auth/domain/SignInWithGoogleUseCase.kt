package io.github.alefaux.foodlist.feature.auth.domain

interface SignInWithGoogleUseCase {
    suspend operator fun invoke(idToken: String): Result<AuthUser>
}
