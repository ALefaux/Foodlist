package io.github.alefaux.foodlist.feature.auth.data.usecase

import io.github.alefaux.foodlist.feature.auth.data.repository.AuthRepository
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import io.github.alefaux.foodlist.feature.auth.domain.SignInWithGoogleUseCase

class SignInWithGoogleUseCaseImpl(
    private val repository: AuthRepository
) : SignInWithGoogleUseCase {

    override suspend fun invoke(idToken: String): Result<AuthUser> = repository.signInWithGoogle(idToken)
}
