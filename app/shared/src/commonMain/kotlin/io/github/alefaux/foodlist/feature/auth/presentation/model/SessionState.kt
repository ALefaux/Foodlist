package io.github.alefaux.foodlist.feature.auth.presentation.model

import io.github.alefaux.foodlist.feature.auth.domain.AuthUser

sealed interface SessionState {
    data object Loading : SessionState
    data object Unauthenticated : SessionState
    data class Authenticated(val user: AuthUser) : SessionState
}
