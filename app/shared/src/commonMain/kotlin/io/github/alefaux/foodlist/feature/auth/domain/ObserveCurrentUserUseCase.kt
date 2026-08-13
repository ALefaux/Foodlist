package io.github.alefaux.foodlist.feature.auth.domain

import kotlinx.coroutines.flow.Flow

interface ObserveCurrentUserUseCase {
    operator fun invoke(): Flow<AuthUser?>
}
